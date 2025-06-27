package com.example.article.lock.notification;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

@Component(
        immediate = true,
        property = "javax.portlet.name=" +
                com.liferay.journal.constants.JournalPortletKeys.JOURNAL,
        service = UserNotificationHandler.class
)
public class ArticleLockNotificationHandler extends BaseUserNotificationHandler {

    public ArticleLockNotificationHandler() {
        setPortletId(com.liferay.journal.constants.JournalPortletKeys.JOURNAL);
    }

    @Override
    protected String getBody(
            UserNotificationEvent userNotificationEvent,
            ServiceContext serviceContext) throws Exception {

        JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
                userNotificationEvent.getPayload());

        String notificationType = jsonObject.getString("notificationType");

        if ("article-control-taken".equals(notificationType)) {
            String newUserName = jsonObject.getString("newUserName");
            String articleTitle = jsonObject.getString("articleTitle", "");
            String articleId = jsonObject.getString("articleId");

            String message = LanguageUtil.format(
                    serviceContext.getLocale(),
                    "notification-article-control-taken-details",
                    new Object[] {newUserName, articleTitle});

            return StringUtil.replace(
                    _BODY_TEMPLATE,
                    new String[] {"[$TITLE$]", "[$BODY_TEXT$]"},
                    new String[] {
                            LanguageUtil.get(serviceContext.getLocale(),
                                    "notification-article-control-taken"),
                            message
                    });
        }

        return "";
    }

    @Override
    protected String getLink(
            UserNotificationEvent userNotificationEvent,
            ServiceContext serviceContext) throws Exception {

        // Retornar link para a lista de artigos
        return serviceContext.getLayoutURL();
    }

    @Override
    protected String getBodyTemplate() throws Exception {
        return _BODY_TEMPLATE;
    }

    private static final String _BODY_TEMPLATE =
            "<div class=\"title\">[$TITLE$]</div>" +
                    "<div class=\"body\">[$BODY_TEXT$]</div>";
}