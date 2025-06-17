package com.example.article.lock.portlet.servlet;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.Enumeration;

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
                "osgi.http.whiteboard.servlet.pattern=/o/article-lock/unlock"
        },
        service = Servlet.class
)
public class ArticleUnlockServlet extends HttpServlet {

    private static final Log _log = LogFactoryUtil.getLog(ArticleUnlockServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        _log.info(">>> ArticleUnlockServlet INITIALIZED at /o/article-lock/unlock");
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        _log.info(">>> ======= ArticleUnlockServlet POST Request =======");
        _log.info(">>> Request URL: " + request.getRequestURL());
        _log.info(">>> Request URI: " + request.getRequestURI());

        // Debug todos os parâmetros
        _log.info(">>> All parameters:");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            _log.info(">>>   " + paramName + " = " + paramValue);
        }

        // Debug headers importantes
        _log.info(">>> Headers:");
        _log.info(">>>   Content-Type: " + request.getContentType());
        _log.info(">>>   User-Agent: " + request.getHeader("User-Agent"));
        _log.info(">>>   Referer: " + request.getHeader("Referer"));

        String articleId = ParamUtil.getString(request, "articleId");
        _log.info(">>> Extracted articleId: " + articleId);

        try {
            if (articleId != null && !articleId.isEmpty()) {
                _log.info(">>> Attempting to unlock article: " + articleId);
                _articleEditLockLocalService.unlockArticle(articleId);
                _log.info(">>> SUCCESS - Lock released for article: " + articleId + " via servlet");

                response.setStatus(HttpServletResponse.SC_OK);
                String responseJson = "{\"success\": true, \"articleId\": \"" + articleId + "\"}";
                ServletResponseUtil.write(response, responseJson);
                _log.info(">>> Response sent: " + responseJson);
            } else {
                _log.warn(">>> WARNING - No articleId provided in request");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                String responseJson = "{\"success\": false, \"error\": \"No articleId\"}";
                ServletResponseUtil.write(response, responseJson);
                _log.info(">>> Error response sent: " + responseJson);
            }
        } catch (Exception e) {
            _log.error(">>> ERROR - Error releasing lock via servlet for article: " + articleId, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String responseJson = "{\"success\": false, \"error\": \"" + e.getMessage() + "\"}";
            ServletResponseUtil.write(response, responseJson);
            _log.info(">>> Error response sent: " + responseJson);
        }

        _log.info(">>> ======= ArticleUnlockServlet Request Complete =======");
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        _log.info(">>> ArticleUnlockServlet GET request received - sending method not allowed");
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        ServletResponseUtil.write(response, "{\"error\": \"Only POST method is allowed\"}");
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}