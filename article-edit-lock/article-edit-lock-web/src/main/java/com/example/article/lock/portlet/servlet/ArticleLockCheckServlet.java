package com.example.article.lock.portlet.servlet;

import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "osgi.http.whiteboard.context.path=/",
                "osgi.http.whiteboard.servlet.pattern=/o/article-lock/check/*"
        },
        service = Servlet.class
)
public class ArticleLockCheckServlet extends HttpServlet {

    private static final Log _log = LogFactoryUtil.getLog(ArticleLockCheckServlet.class);

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        _log.debug(">>> Check lock request: " + pathInfo);

        if (pathInfo != null && pathInfo.length() > 1) {
            String[] parts = pathInfo.substring(1).split("/");

            if (parts.length >= 2) {
                String articleId = parts[0];
                long userId = Long.parseLong(parts[1]);

                _log.debug(">>> Checking lock for article: " + articleId + ", user: " + userId);

                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();

                try {
                    ArticleEditLock lock = _articleEditLockLocalService.getActiveArticleLock(articleId);

                    if (lock == null || lock.getUserId() == userId) {
                        // Usuário ainda tem o lock ou não há lock
                        writer.write("{\"lostControl\": false}");
                    } else {
                        // Outro usuário tem o lock
                        _log.info(">>> User " + userId + " lost control of article " + articleId);
                        writer.write("{\"lostControl\": true}");
                    }
                } catch (Exception e) {
                    _log.error("Error checking lock", e);
                    writer.write("{\"error\": true}");
                }

                writer.flush();
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}