package com.example.article.lock.notification;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ArticleControlNotificationService.class)
public class ArticleControlNotificationServiceImpl implements ArticleControlNotificationService {

    private static final Log _log = LogFactoryUtil.getLog(ArticleControlNotificationServiceImpl.class);

    @Reference
    private UserNotificationEventLocalService _userNotificationEventLocalService;

    @Reference
    private UserLocalService _userLocalService;

    @Override
    public void sendControlTakenNotification(
        long previousUserId,
        long newUserId,
        String articleId,
        String articleTitle,
        ServiceContext serviceContext) throws Exception {

        try {
            User newUser = _userLocalService.getUser(newUserId);
            User previousUser = _userLocalService.getUser(previousUserId);

            String message = String.format(
                "Sua sessão de edição foi transferida para %s. O artigo não está mais sob seu controle.",
                newUser.getFullName()
            );

            JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
            payloadJSON.put("articleId", articleId);
            payloadJSON.put("articleTitle", articleTitle);
            payloadJSON.put("newUserId", newUserId);
            payloadJSON.put("newUserName", newUser.getFullName());
            payloadJSON.put("previousUserId", previousUserId);
            payloadJSON.put("previousUserName", previousUser.getFullName());
            payloadJSON.put("message", message);
            payloadJSON.put("timestamp", System.currentTimeMillis());

            _userNotificationEventLocalService.addUserNotificationEvent(
                previousUserId,
                ArticleControlNotificationHandler.PORTLET_ID,
                System.currentTimeMillis(),
                UserNotificationDeliveryConstants.TYPE_WEBSITE,
                newUserId,
                payloadJSON.toString(),
                false,
                serviceContext
            );

            _log.info(String.format(
                "Notificação de controle de artigo enviada: usuário %s (%d) assumiu controle do artigo %s do usuário %s (%d)",
                newUser.getFullName(), newUserId, articleId, previousUser.getFullName(), previousUserId
            ));

        } catch (Exception e) {
            _log.error("Erro ao enviar notificação de controle de artigo", e);
            throw e;
        }
    }
}