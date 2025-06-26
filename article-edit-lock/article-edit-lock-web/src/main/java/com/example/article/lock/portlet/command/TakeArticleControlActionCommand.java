package com.example.article.lock.portlet.command;

import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.notification.ArticleControlNotificationService;
import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
                "mvc.command.name=/journal/take_article_control"
        },
        service = MVCActionCommand.class
)
public class TakeArticleControlActionCommand extends BaseMVCActionCommand {

    private static final Log _log = LogFactoryUtil.getLog(
            TakeArticleControlActionCommand.class);

    @Override
    protected void doProcessAction(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        _log.info("=== TakeArticleControlActionCommand START ===");

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(
                WebKeys.THEME_DISPLAY);

        String articleId = ParamUtil.getString(actionRequest, "articleId");

        _log.info("Taking control of article: " + articleId +
                " for user: " + themeDisplay.getUserId());

        try {
            ServiceContext serviceContext = ServiceContextFactory.getInstance(
                    actionRequest);

            // Verificar quem tinha o controle anterior antes de tomar controle
            ArticleEditLock existingLock = _articleEditLockLocalService.getActiveArticleLock(articleId);
            long previousUserId = 0;
            if (existingLock != null && existingLock.isLocked()) {
                previousUserId = existingLock.getUserId();
                _log.info("Previous user had control: " + previousUserId);
            }

            // Tomar controle do artigo
            _articleEditLockLocalService.takeControlOfArticle(
                    articleId,
                    themeDisplay.getUserId(),
                    serviceContext);

            // Enviar notificação ao usuário anterior se houver
            if (previousUserId > 0 && previousUserId != themeDisplay.getUserId()) {
                try {
                    // Buscar informações do artigo para a notificação
                    String articleTitle = articleId;
                    try {
                        JournalArticle article = _journalArticleLocalService.getLatestArticle(
                            themeDisplay.getScopeGroupId(), articleId);
                        if (article != null && Validator.isNotNull(article.getTitle())) {
                            articleTitle = article.getTitle(themeDisplay.getLocale());
                        }
                    } catch (Exception e) {
                        _log.warn("Could not fetch article title for: " + articleId, e);
                    }

                    _articleControlNotificationService.sendControlTakenNotification(
                        previousUserId,
                        themeDisplay.getUserId(),
                        articleId,
                        articleTitle,
                        serviceContext
                    );

                    _log.info("Notification sent to previous user: " + previousUserId);
                } catch (Exception e) {
                    _log.error("Failed to send notification to previous user", e);
                    // Não falhar a operação principal por causa da notificação
                }
            }

            SessionMessages.add(actionRequest, "article-control-taken");

            _log.info("Control taken successfully");

            // Redirecionar para edição do artigo
            String redirect = _portal.escapeRedirect(
                    ParamUtil.getString(actionRequest, "redirect"));

            if (redirect == null || redirect.isEmpty()) {
                // Construir URL de edição se não houver redirect
                redirect = themeDisplay.getPortalURL() +
                        themeDisplay.getURLCurrent() +
                        "&_" + JournalPortletKeys.JOURNAL +
                        "_mvcRenderCommandName=/journal/edit_article" +
                        "&_" + JournalPortletKeys.JOURNAL +
                        "_articleId=" + articleId;
            }

            actionResponse.sendRedirect(redirect);

        } catch (Exception e) {
            _log.error("Error taking control of article: " + articleId, e);
            SessionErrors.add(actionRequest, e.getClass(), e);

            // Redirecionar de volta
            actionResponse.sendRedirect(
                    ParamUtil.getString(actionRequest, "backURL",
                            themeDisplay.getURLCurrent()));
        }

        _log.info("=== TakeArticleControlActionCommand END ===");
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;

    @Reference
    private ArticleControlNotificationService _articleControlNotificationService;

    @Reference
    private JournalArticleLocalService _journalArticleLocalService;

    @Reference
    private Portal _portal;
}