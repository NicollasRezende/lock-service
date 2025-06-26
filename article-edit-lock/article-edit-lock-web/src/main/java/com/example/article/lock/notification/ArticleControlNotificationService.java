package com.example.article.lock.notification;

import com.liferay.portal.kernel.service.ServiceContext;

public interface ArticleControlNotificationService {

    void sendControlTakenNotification(
        long previousUserId,
        long newUserId,
        String articleId,
        String articleTitle,
        ServiceContext serviceContext) throws Exception;
}