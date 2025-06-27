package com.example.article.lock.portlet;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = {
        "javax.portlet.name=" + JournalPortletKeys.JOURNAL
}, service = javax.portlet.filter.PortletFilter.class)
public class JournalPublishActionFilter implements ActionFilter {

    private static final Log _log = LogFactoryUtil.getLog(JournalPublishActionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws PortletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(
            ActionRequest actionRequest, ActionResponse actionResponse,
            FilterChain filterChain)
            throws IOException, PortletException {

        String cmd = ParamUtil.getString(actionRequest, "cmd");
        String articleId = ParamUtil.getString(actionRequest, "articleId");
        String workflowAction = ParamUtil.getString(actionRequest, "workflowAction");
        String actionName = actionRequest.getParameter(ActionRequest.ACTION_NAME);

        // IMPORTANTE: Primeiro executa a ação original
        filterChain.doFilter(actionRequest, actionResponse);

        // Depois de executar a ação original com sucesso, libera o lock
        boolean shouldReleaseLock = false;

        if ("/journal/publish_article".equals(actionName) ||
                "/journal/update_article".equals(actionName) ||
                "/journal/add_article".equals(actionName)) {

            if ("1".equals(workflowAction)) {
                shouldReleaseLock = true;
            } else if ("2".equals(workflowAction)) {
                shouldReleaseLock = true;
            } else if (articleId != null && !articleId.isEmpty()) {
                shouldReleaseLock = true;
            }
        }

        if (shouldReleaseLock && articleId != null && !articleId.isEmpty()) {
            try {
                _articleEditLockLocalService.unlockArticle(articleId);
            } catch (Exception e) {
                _log.error("Error releasing lock", e);
            }
        }
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}