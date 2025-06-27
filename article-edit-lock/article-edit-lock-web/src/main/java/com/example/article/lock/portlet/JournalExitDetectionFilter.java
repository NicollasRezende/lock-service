package com.example.article.lock.portlet;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.Enumeration;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = {
        "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
        "service.ranking:Integer=50"
}, service = javax.portlet.filter.PortletFilter.class)
public class JournalExitDetectionFilter implements RenderFilter {

    private static final String EDITING_ARTICLE_KEY = "EDITING_ARTICLE_ID";

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

        HttpServletRequest httpRequest = com.liferay.portal.kernel.util.PortalUtil.getHttpServletRequest(renderRequest);
        HttpSession session = httpRequest.getSession();

        String cmd = ParamUtil.getString(renderRequest, "cmd");
        String articleId = ParamUtil.getString(renderRequest, "articleId");
        String mvcPath = ParamUtil.getString(renderRequest, "mvcPath", "");
        String mvcRenderCommandName = ParamUtil.getString(renderRequest, "mvcRenderCommandName", "");

        // Detecta se está editando um artigo
        boolean isEditingArticle = "edit".equals(cmd) ||
                "add".equals(cmd) ||
                mvcPath.contains("edit_article") ||
                "/journal/edit_article".equals(mvcRenderCommandName);

        // Verifica o que está na sessão atualmente
        String currentSessionArticleId = (String) session.getAttribute(EDITING_ARTICLE_KEY);

        if (isEditingArticle && articleId != null && !articleId.isEmpty()) {
            // Marca que está editando este artigo
            session.setAttribute(EDITING_ARTICLE_KEY, articleId);

        } else {
            // Não está mais editando - verifica se havia um artigo sendo editado
            String previousArticleId = (String) session.getAttribute(EDITING_ARTICLE_KEY);

            if (previousArticleId != null) {

                try {
                    // Obtém o ID do usuário atual
                    com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay = (com.liferay.portal.kernel.theme.ThemeDisplay) renderRequest
                            .getAttribute(
                                    com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

                    if (themeDisplay != null) {
                        long currentUserId = themeDisplay.getUserId();

                        // CRÍTICO: Só remove o lock se o usuário atual realmente tem o controle
                        com.example.article.lock.model.ArticleEditLock activeLock = _articleEditLockLocalService
                                .getActiveArticleLock(previousArticleId);

                        if (activeLock != null) {
                            if (activeLock.getUserId() == currentUserId) {
                                // O usuário que está saindo realmente tinha o controle
                                _articleEditLockLocalService.unlockArticle(previousArticleId);
                            }
                            // Caso contrário, outro usuário tem o controle - NÃO remove o lock
                        }
                        // Se não há lock ativo, artigo já estava livre
                    }
                } catch (Exception e) {
                    // Silently fail
                }

                // Remove da sessão independentemente (a sessão é local do usuário)
                session.removeAttribute(EDITING_ARTICLE_KEY);
            }
        }

        // Continua o processamento normal
        filterChain.doFilter(renderRequest, renderResponse);
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}