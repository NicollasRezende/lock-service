package com.example.article.lock.portlet.action;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
                "mvc.command.name=/journal/publish_article",
                "mvc.command.name=/journal/update_article",
                "mvc.command.name=/journal/add_article",
                "service.ranking:Integer=10000"
        },
        service = MVCActionCommand.class
)
public class JournalPublishInterceptor extends BaseMVCActionCommand {

    private static final Log _log = LogFactoryUtil.getLog(JournalPublishInterceptor.class);

    @Override
    protected void doProcessAction(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        String cmd = ParamUtil.getString(actionRequest, "cmd");
        String articleId = ParamUtil.getString(actionRequest, "articleId");
        String workflowAction = ParamUtil.getString(actionRequest, "workflowAction");

        _log.info(">>> INTERCEPTOR: Action detected!");
        _log.info(">>> ArticleId: " + articleId);
        _log.info(">>> WorkflowAction: " + workflowAction);

        boolean shouldReleaseLock = false;

        if ("1".equals(workflowAction)) {
            _log.info(">>> Article PUBLISHED");
            shouldReleaseLock = true;
        }
        else if ("2".equals(workflowAction)) {
            _log.info(">>> Article saved as DRAFT");
            shouldReleaseLock = true;
        }
        else if (articleId != null && !articleId.isEmpty()) {
            _log.info(">>> Article SAVED");
            shouldReleaseLock = true;
        }

        if (shouldReleaseLock && articleId != null && !articleId.isEmpty()) {
            try {
                _articleEditLockLocalService.unlockArticle(articleId);
                _log.info(">>> Lock released for: " + articleId);
            } catch (Exception e) {
                _log.error("Error releasing lock", e);
            }
        }
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}