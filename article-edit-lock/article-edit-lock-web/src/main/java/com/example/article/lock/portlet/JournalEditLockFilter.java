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
                        renderLockModal(renderResponse, editingUser, themeDisplay, articleId);
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

                            renderLockModal(renderResponse, editingUser, themeDisplay, articleId);
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

    private void renderLockModal(RenderResponse renderResponse, User editingUser,
                                 ThemeDisplay themeDisplay, String articleId)
            throws IOException {

        renderResponse.setContentType("text/html");
        PrintWriter writer = renderResponse.getWriter();

        String editingUserName = HtmlUtil.escape(editingUser.getFullName());
        String editingUserId = String.valueOf(editingUser.getUserId());

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'>");
        writer.println("<link rel='stylesheet' href='" + themeDisplay.getCDNHost() +
                "/o/frontend-theme-font-awesome-web/css/main.css'>");
        writer.println("<link rel='stylesheet' href='" + themeDisplay.getPortalURL() +
                "/o/frontend-css-web/main.css'>");
        writer.println("<style>");
        writer.println("  .article-lock-modal { padding: 2rem; }");
        writer.println("  .modal-actions { margin-top: 1.5rem; }");
        writer.println("  .btn + .btn { margin-left: 0.5rem; }");
        writer.println("</style>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<div class='container-fluid article-lock-modal'>");
        writer.println("  <div class='row justify-content-center'>");
        writer.println("    <div class='col-md-6'>");
        writer.println("      <div class='card'>");
        writer.println("        <div class='card-header'>");
        writer.println("          <h3 class='card-title'>" +
                LanguageUtil.get(themeDisplay.getLocale(), "article-being-edited") +
                "</h3>");
        writer.println("        </div>");
        writer.println("        <div class='card-body'>");
        writer.println("          <div class='alert alert-warning' role='alert'>");
        writer.println("            <span class='alert-indicator'>");
        writer.println("              <svg class='lexicon-icon lexicon-icon-warning-full'>");
        writer.println("                <use href='" + themeDisplay.getPathThemeImages() +
                "/clay/icons.svg#warning-full'></use>");
        writer.println("              </svg>");
        writer.println("            </span>");
        writer.println("            <div class='alert-content'>");
        writer.println("              <strong class='lead'>" +
                LanguageUtil.get(themeDisplay.getLocale(), "article-locked-title") +
                "</strong>");
        writer.println("              <br/>");
        writer.println("              <p>" +
                LanguageUtil.format(themeDisplay.getLocale(),
                        "article-is-being-edited-by-user",
                        new String[]{editingUserName, editingUserId}) +
                "</p>");
        writer.println("              <p>" +
                LanguageUtil.get(themeDisplay.getLocale(),
                        "article-lock-timeout-message") +
                "</p>");
        writer.println("            </div>");
        writer.println("          </div>");
        writer.println("        </div>");
        writer.println("        <div class='card-footer modal-actions'>");

        // Botão Voltar
        writer.println("          <button type='button' class='btn btn-secondary' " +
                "onclick='window.history.back();'>");
        writer.println("            <span class='lfr-btn-label'>" +
                LanguageUtil.get(themeDisplay.getLocale(), "go-back") +
                "</span>");
        writer.println("          </button>");

        // Botão Tomar Controle - SEMPRE VISÍVEL
        writer.println("          <button type='button' class='btn btn-warning' " +
                "id='takeControlBtn' " +
                "data-article-id='" + HtmlUtil.escapeAttribute(articleId) + "'>");
        writer.println("            <span class='lfr-btn-label'>" +
                LanguageUtil.get(themeDisplay.getLocale(), "take-control") +
                "</span>");
        writer.println("          </button>");

        writer.println("        </div>");
        writer.println("      </div>");
        writer.println("    </div>");
        writer.println("  </div>");
        writer.println("</div>");

        // JavaScript
        writer.println("<script>");
        writer.println("(function() {");

        // Script do botão Tomar Controle
        writer.println("  var takeControlBtn = document.getElementById('takeControlBtn');");
        writer.println("  if (takeControlBtn) {");
        writer.println("    takeControlBtn.addEventListener('click', function(e) {");
        writer.println("      e.preventDefault();");
        writer.println("      ");
        writer.println("      var articleId = this.getAttribute('data-article-id');");
        writer.println("      var confirmMsg = '" +
                HtmlUtil.escapeJS(LanguageUtil.get(themeDisplay.getLocale(),
                        "confirm-take-control")) + "';");
        writer.println("      ");
        writer.println("      if (confirm(confirmMsg)) {");
        writer.println("        var form = document.createElement('form');");
        writer.println("        form.method = 'POST';");
        writer.println("        form.action = '" + renderResponse.createActionURL() + "';");
        writer.println("        ");
        writer.println("        var cmdInput = document.createElement('input');");
        writer.println("        cmdInput.type = 'hidden';");
        writer.println("        cmdInput.name = 'javax.portlet.action';");
        writer.println("        cmdInput.value = '/journal/take_article_control';");
        writer.println("        form.appendChild(cmdInput);");
        writer.println("        ");
        writer.println("        var articleInput = document.createElement('input');");
        writer.println("        articleInput.type = 'hidden';");
        writer.println("        articleInput.name = 'articleId';");
        writer.println("        articleInput.value = articleId;");
        writer.println("        form.appendChild(articleInput);");
        writer.println("        ");

        // Criar URL de redirect para edição
        String editUrl = themeDisplay.getPortalURL() +
                themeDisplay.getURLCurrent() +
                "&_" + JournalPortletKeys.JOURNAL +
                "_mvcRenderCommandName=/journal/edit_article" +
                "&_" + JournalPortletKeys.JOURNAL +
                "_articleId=" + articleId;

        writer.println("        var redirectInput = document.createElement('input');");
        writer.println("        redirectInput.type = 'hidden';");
        writer.println("        redirectInput.name = 'redirect';");
        writer.println("        redirectInput.value = '" +
                HtmlUtil.escapeJS(editUrl) + "';");
        writer.println("        form.appendChild(redirectInput);");
        writer.println("        ");
        writer.println("        var backURLInput = document.createElement('input');");
        writer.println("        backURLInput.type = 'hidden';");
        writer.println("        backURLInput.name = 'backURL';");
        writer.println("        backURLInput.value = window.location.href;");
        writer.println("        form.appendChild(backURLInput);");
        writer.println("        ");
        writer.println("        document.body.appendChild(form);");
        writer.println("        form.submit();");
        writer.println("      }");
        writer.println("    });");
        writer.println("  }");

        writer.println("})();");
        writer.println("</script>");

        writer.println("</body>");
        writer.println("</html>");
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}