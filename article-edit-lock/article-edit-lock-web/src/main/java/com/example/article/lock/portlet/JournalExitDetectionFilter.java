package com.example.article.lock.portlet;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
                "service.ranking:Integer=50"
        },
        service = javax.portlet.filter.PortletFilter.class
)
public class JournalExitDetectionFilter implements RenderFilter {

    private static final Log _log = LogFactoryUtil.getLog(JournalExitDetectionFilter.class);
    private static final String EDITING_ARTICLE_KEY = "EDITING_ARTICLE_ID";

    @Override
    public void init(FilterConfig filterConfig) throws PortletException {
        _log.info(">>> JournalExitDetectionFilter INITIALIZED");
    }

    @Override
    public void destroy() {
        _log.info(">>> JournalExitDetectionFilter DESTROYED");
    }

    @Override
    public void doFilter(
            RenderRequest renderRequest, RenderResponse renderResponse,
            FilterChain filterChain)
            throws IOException, PortletException {

        _log.info(">>> ======= JournalExitDetectionFilter START =======");

        // Debug: Listar todos os parâmetros
        _log.info(">>> All parameters:");
        Enumeration<String> paramNames = renderRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = renderRequest.getParameter(paramName);
            _log.info(">>>   " + paramName + " = " + paramValue);
        }

        HttpServletRequest httpRequest = com.liferay.portal.kernel.util.PortalUtil.getHttpServletRequest(renderRequest);
        HttpSession session = httpRequest.getSession();

        String cmd = ParamUtil.getString(renderRequest, "cmd");
        String articleId = ParamUtil.getString(renderRequest, "articleId");
        String mvcPath = ParamUtil.getString(renderRequest, "mvcPath", "");
        String mvcRenderCommandName = ParamUtil.getString(renderRequest, "mvcRenderCommandName", "");

        _log.info(">>> Extracted values:");
        _log.info(">>>   cmd: " + cmd);
        _log.info(">>>   articleId: " + articleId);
        _log.info(">>>   mvcPath: " + mvcPath);
        _log.info(">>>   mvcRenderCommandName: " + mvcRenderCommandName);
        _log.info(">>>   Request URL: " + httpRequest.getRequestURL());

        // Detecta se está editando um artigo
        boolean isEditingArticle = "edit".equals(cmd) ||
                "add".equals(cmd) ||
                mvcPath.contains("edit_article") ||
                "/journal/edit_article".equals(mvcRenderCommandName);

        _log.info(">>> Is editing article? " + isEditingArticle);

        // Verifica o que está na sessão atualmente
        String currentSessionArticleId = (String) session.getAttribute(EDITING_ARTICLE_KEY);
        _log.info(">>> Current article in session: " + currentSessionArticleId);

        if (isEditingArticle && articleId != null && !articleId.isEmpty()) {
            // Marca que está editando este artigo
            _log.info(">>> STORING in session - User started editing article: " + articleId);
            session.setAttribute(EDITING_ARTICLE_KEY, articleId);

        } else {
            _log.info(">>> NOT editing article page");

            // Não está mais editando - verifica se havia um artigo sendo editado
            String previousArticleId = (String) session.getAttribute(EDITING_ARTICLE_KEY);

            if (previousArticleId != null) {
                _log.info(">>> DETECTED EXIT - User left article editing page. Previous article: " + previousArticleId);

                try {
                    // Libera o lock do artigo anterior
                    _articleEditLockLocalService.unlockArticle(previousArticleId);
                    _log.info(">>> SUCCESS - Lock released for article: " + previousArticleId);
                } catch (Exception e) {
                    _log.error(">>> ERROR - Error releasing lock on navigation", e);
                }

                // Remove da sessão
                session.removeAttribute(EDITING_ARTICLE_KEY);
                _log.info(">>> Session cleared");
            } else {
                _log.info(">>> No previous article in session to unlock");
            }
        }

        _log.info(">>> ======= JournalExitDetectionFilter END - Calling chain =======");

        // Continua o processamento normal
        filterChain.doFilter(renderRequest, renderResponse);
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}