package com.example.article.lock.portlet;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
                "service.ranking:Integer=100"
        },
        service = javax.portlet.filter.PortletFilter.class
)
public class JournalHeaderPortalPortletFilter implements RenderFilter {

    private static final Log _log = LogFactoryUtil.getLog(JournalHeaderPortalPortletFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws PortletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(
            RenderRequest renderRequest, RenderResponse renderResponse,
            FilterChain filterChain)
            throws IOException, PortletException {

        String mvcRenderCommandName = ParamUtil.getString(renderRequest, "mvcRenderCommandName");

        if ("/journal/edit_article".equals(mvcRenderCommandName)) {
            String articleId = ParamUtil.getString(renderRequest, "articleId");

            if (articleId != null && !articleId.isEmpty()) {
                String scriptContent =
                        "<script>" +
                                "(function() {" +
                                "    console.log('Article Lock Handler inline loaded');" +
                                "    var articleId = '" + articleId + "';" +
                                "    " +
                                "    function releaseArticleLock() {" +
                                "        if (articleId) {" +
                                "            console.log('Releasing lock for:', articleId);" +
                                "            var xhr = new XMLHttpRequest();" +
                                "            xhr.open('POST', '/o/journal/article_lock/unlock', true);" +
                                "            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');" +
                                "            xhr.setRequestHeader('p_auth', Liferay.authToken);" +
                                "            xhr.send('articleId=' + articleId);" +
                                "        }" +
                                "    }" +
                                "    " +
                                "    window.addEventListener('beforeunload', function(e) {" +
                                "        releaseArticleLock();" +
                                "    });" +
                                "    " +
                                "    Liferay.on('beforeNavigate', function(event) {" +
                                "        var destination = event.path || '';" +
                                "        if (destination.indexOf('edit_article') === -1) {" +
                                "            releaseArticleLock();" +
                                "        }" +
                                "    });" +
                                "    " +
                                "    setTimeout(function() {" +
                                "        var cancelButtons = document.querySelectorAll('.btn-cancel, .btn-secondary');" +
                                "        cancelButtons.forEach(function(button) {" +
                                "            button.addEventListener('click', function(e) {" +
                                "                console.log('Cancel clicked');" +
                                "                releaseArticleLock();" +
                                "            });" +
                                "        });" +
                                "    }, 1000);" +
                                "})();" +
                                "</script>";

                renderResponse.getWriter().write(scriptContent);
            }
        }

        filterChain.doFilter(renderRequest, renderResponse);
    }

    @Reference
    private Portal _portal;
}