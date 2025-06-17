package com.example.article.lock.portlet;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.util.Enumeration;

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
        _log.info(">>> JournalHeaderPortalPortletFilter INITIALIZED");
    }

    @Override
    public void destroy() {
        _log.info(">>> JournalHeaderPortalPortletFilter DESTROYED");
    }

    @Override
    public void doFilter(
            RenderRequest renderRequest, RenderResponse renderResponse,
            FilterChain filterChain)
            throws IOException, PortletException {

        _log.info(">>> ======= JournalHeaderPortalPortletFilter START =======");

        // Debug: Listar alguns parâmetros importantes
        String mvcRenderCommandName = ParamUtil.getString(renderRequest, "mvcRenderCommandName");
        String cmd = ParamUtil.getString(renderRequest, "cmd");
        String articleId = ParamUtil.getString(renderRequest, "articleId");
        String mvcPath = ParamUtil.getString(renderRequest, "mvcPath", "");

        _log.info(">>> Header Filter - Parameters:");
        _log.info(">>>   mvcRenderCommandName: " + mvcRenderCommandName);
        _log.info(">>>   cmd: " + cmd);
        _log.info(">>>   articleId: " + articleId);
        _log.info(">>>   mvcPath: " + mvcPath);

        // Injeta o script apenas quando está editando um artigo
        boolean shouldInjectScript = "/journal/edit_article".equals(mvcRenderCommandName) ||
                "edit".equals(cmd) ||
                "add".equals(cmd) ||
                mvcPath.contains("edit_article");

        _log.info(">>> Should inject script? " + shouldInjectScript);

        if (shouldInjectScript) {
            _log.info(">>> INJECTING article lock script");

            // Script mínimo apenas para capturar fechamento de aba
            // O JournalExitDetectionFilter cuida dos outros casos
            String scriptContent =
                    "<script>" +
                            "console.log('>>> Article lock script injected');" +
                            "(function() {" +
                            "    var lockReleased = false;" +
                            "    " +
                            "    // Marca como liberado quando o form é submetido" +
                            "    document.addEventListener('submit', function() {" +
                            "        console.log('>>> Form submitted, marking lock as released');" +
                            "        lockReleased = true;" +
                            "    });" +
                            "    " +
                            "    // Libera lock apenas ao fechar aba/janela" +
                            "    window.addEventListener('beforeunload', function() {" +
                            "        if (!lockReleased) {" +
                            "            console.log('>>> beforeunload triggered, checking for articleId');" +
                            "            var articleIdInput = document.querySelector('input[name*=\"articleId\"]');" +
                            "            if (articleIdInput && articleIdInput.value) {" +
                            "                console.log('>>> Releasing lock for article:', articleIdInput.value);" +
                            "                // Requisição síncrona para liberar o lock" +
                            "                var xhr = new XMLHttpRequest();" +
                            "                xhr.open('POST', '/o/article-lock/unlock', false);" +
                            "                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');" +
                            "                xhr.send('articleId=' + articleIdInput.value);" +
                            "                console.log('>>> Lock release request sent');" +
                            "            } else {" +
                            "                console.log('>>> No articleId found in page');" +
                            "            }" +
                            "        } else {" +
                            "            console.log('>>> Lock already released, skipping');" +
                            "        }" +
                            "    });" +
                            "})();" +
                            "</script>";

            renderResponse.getWriter().write(scriptContent);

            _log.info(">>> Article lock script injection completed");
        } else {
            _log.info(">>> NOT injecting script - not an edit page");
        }

        _log.info(">>> ======= JournalHeaderPortalPortletFilter END - Calling chain =======");

        // Continua o processamento normal
        filterChain.doFilter(renderRequest, renderResponse);
    }

    @Reference
    private Portal _portal;
}