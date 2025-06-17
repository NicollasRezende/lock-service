package com.example.article.lock.portlet;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
                "service.ranking:Integer=1000"
        },
        service = javax.portlet.filter.PortletFilter.class
)
public class JournalEditLockFilter implements RenderFilter {

    private static final Log _log = LogFactoryUtil.getLog(JournalEditLockFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws PortletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(
            RenderRequest renderRequest, RenderResponse renderResponse,
            FilterChain filterChain)
            throws IOException, PortletException {

        String mvcRenderCommandName = ParamUtil.getString(renderRequest, "mvcRenderCommandName");
        String articleId = ParamUtil.getString(renderRequest, "articleId");

        _log.debug("JournalEditLockFilter - mvcRenderCommandName: " + mvcRenderCommandName +
                ", articleId: " + articleId);

        // Verifica se está editando um artigo existente
        if ("/journal/edit_article".equals(mvcRenderCommandName) &&
                articleId != null && !articleId.isEmpty()) {

            try {
                ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(
                        WebKeys.THEME_DISPLAY);

                ServiceContext serviceContext = ServiceContextFactory.getInstance(
                        renderRequest);

                _log.info("Checking lock for article: " + articleId +
                        ", current user: " + themeDisplay.getUserId());

                // Verifica se está bloqueado por outro usuário
                if (_articleEditLockLocalService.isArticleLockedByOtherUser(
                        articleId, themeDisplay.getUserId())) {

                    User editingUser = _articleEditLockLocalService.getEditingUser(articleId);

                    if (editingUser != null) {
                        _log.info("Article " + articleId + " is locked by user: " +
                                editingUser.getFullName() + " (ID: " + editingUser.getUserId() + ")");

                        // Renderiza o modal diretamente
                        renderLockModal(renderResponse, editingUser, themeDisplay);
                        return;
                    }
                } else {
                    // Tenta criar o lock
                    boolean lockCreated = _articleEditLockLocalService.tryLockArticle(
                            articleId, themeDisplay.getUserId(), serviceContext);

                    if (!lockCreated) {
                        User editingUser = _articleEditLockLocalService.getEditingUser(articleId);

                        if (editingUser != null) {
                            _log.warn("Failed to create lock for article: " + articleId +
                                    ", being edited by: " + editingUser.getFullName());

                            renderLockModal(renderResponse, editingUser, themeDisplay);
                            return;
                        }
                    } else {
                        _log.info("Lock created/updated successfully for article: " + articleId +
                                ", user: " + themeDisplay.getUser().getFullName());
                    }
                }
            } catch (Exception e) {
                _log.error("Error in JournalEditLockFilter", e);
                e.printStackTrace();
            }
        }

        // Continua com o processamento normal se não houver bloqueio
        filterChain.doFilter(renderRequest, renderResponse);
    }

    private void renderLockModal(RenderResponse renderResponse, User editingUser, ThemeDisplay themeDisplay)
            throws IOException {

        renderResponse.setContentType("text/html");
        PrintWriter writer = renderResponse.getWriter();

        String editingUserName = HtmlUtil.escape(editingUser.getFullName());
        String editingUserId = String.valueOf(editingUser.getUserId());

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'>");
        writer.println("<link rel='stylesheet' href='" + themeDisplay.getCDNHost() + "/o/frontend-theme-font-awesome-web/css/main.css'>");
        writer.println("<link rel='stylesheet' href='" + themeDisplay.getPortalURL() + "/o/frontend-css-web/main.css'>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<div class='container-fluid mt-5'>");
        writer.println("  <div class='row justify-content-center'>");
        writer.println("    <div class='col-md-6'>");
        writer.println("      <div class='card'>");
        writer.println("        <div class='card-header'>");
        writer.println("          <h3 class='card-title'>" + LanguageUtil.get(themeDisplay.getLocale(), "Artigo em Edição") + "</h3>");
        writer.println("        </div>");
        writer.println("        <div class='card-body'>");
        writer.println("          <div class='alert alert-warning' role='alert'>");
        writer.println("            <span class='alert-indicator'>");
        writer.println("              <svg class='lexicon-icon lexicon-icon-warning-full'>");
        writer.println("                <use href='" + themeDisplay.getPathThemeImages() + "/clay/icons.svg#warning-full'></use>");
        writer.println("              </svg>");
        writer.println("            </span>");
        writer.println("            <div class='alert-content'>");
        writer.println("              <strong class='lead'>" + LanguageUtil.get(themeDisplay.getLocale(), "Este artigo está sendo editado") + "</strong>");
        writer.println("              <br/>");
        writer.println("              <p>" + LanguageUtil.format(themeDisplay.getLocale(), "Este artigo está sendo editado pelo usuário", new String[]{editingUserName, editingUserId}) + "</p>");
        writer.println("              <p>" + LanguageUtil.get(themeDisplay.getLocale(), "O bloqueio será liberado automaticamente após 2 horas de inatividade.") + "</p>");
        writer.println("            </div>");
        writer.println("          </div>");
        writer.println("        </div>");
        writer.println("        <div class='card-footer'>");
        writer.println("          <button type='button' class='btn btn-secondary' onclick='window.history.back();'>");
        writer.println("            " + LanguageUtil.get(themeDisplay.getLocale(), "go-back"));
        writer.println("          </button>");
        writer.println("        </div>");
        writer.println("      </div>");
        writer.println("    </div>");
        writer.println("  </div>");
        writer.println("</div>");

        writer.println("</body>");
        writer.println("</html>");
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}