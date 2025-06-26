package com.example.article.lock.notification;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + ArticleControlNotificationHandler.PORTLET_ID
    },
    service = com.liferay.portal.kernel.notifications.UserNotificationHandler.class
)
public class ArticleControlNotificationHandler extends BaseUserNotificationHandler {

    public static final String PORTLET_ID = "article_control_notification_portlet";

    public ArticleControlNotificationHandler() {
        setPortletId(PORTLET_ID);
    }

    @Override
    protected String getBody(
        UserNotificationEvent userNotificationEvent,
        ServiceContext serviceContext) throws Exception {

        JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
            userNotificationEvent.getPayload());

        String articleTitle = jsonObject.getString("articleTitle");
        String newUserName = jsonObject.getString("newUserName");
        String message = jsonObject.getString("message");

        String body = StringUtil.replace(
            getNotificationTemplate(),
            new String[] {
                "[$ARTICLE_TITLE$]",
                "[$NEW_USER_NAME$]", 
                "[$MESSAGE$]"
            },
            new String[] {
                articleTitle,
                newUserName,
                message
            }
        );

        return body;
    }

    @Override
    protected String getLink(
        UserNotificationEvent userNotificationEvent,
        ServiceContext serviceContext) throws Exception {

        JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
            userNotificationEvent.getPayload());

        String articleId = jsonObject.getString("articleId");
        return "/web/guest/journal?articleId=" + articleId;
    }

    protected String getNotificationTemplate() {
        return "<div class=\"notification-content article-control-notification\">" +
               "<div class=\"notification-header\">" +
               "<i class=\"icon-lock\"></i>" +
               "<strong>Controle de Artigo Transferido</strong>" +
               "</div>" +
               "<div class=\"notification-body\">" +
               "<p>O usuário <strong>[$NEW_USER_NAME$]</strong> assumiu o controle do artigo:</p>" +
               "<p class=\"article-title\">\"[$ARTICLE_TITLE$]\"</p>" +
               "<p class=\"notification-message\">[$MESSAGE$]</p>" +
               "</div>" +
               "</div>";
    }
}