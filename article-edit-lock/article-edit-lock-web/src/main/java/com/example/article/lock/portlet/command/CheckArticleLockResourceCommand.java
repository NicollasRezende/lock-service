package com.example.article.lock.portlet.command;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = {
        "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
        "mvc.command.name=/journal/check_article_lock"
}, service = MVCResourceCommand.class)
public class CheckArticleLockResourceCommand extends BaseMVCResourceCommand {

    @Override
    protected void doServeResource(
            ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws Exception {

        String articleId = ParamUtil.getString(resourceRequest, "articleId");
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

        try {
            if (articleId != null && !articleId.isEmpty()) {

                // Busca o lock ativo diretamente no banco
                com.example.article.lock.model.ArticleEditLock currentLock = _articleEditLockLocalService
                        .getActiveArticleLock(articleId);

                boolean hasControl = false;
                String currentEditorName = null;
                long currentEditorId = 0;

                if (currentLock != null) {
                    // Verifica se o usuário atual é o dono do lock
                    hasControl = (currentLock.getUserId() == themeDisplay.getUserId());
                    currentEditorId = currentLock.getUserId();

                    // Busca o nome do editor atual
                    try {
                        com.liferay.portal.kernel.model.User currentEditor = com.liferay.portal.kernel.service.UserLocalServiceUtil
                                .getUser(currentLock.getUserId());
                        currentEditorName = currentEditor.getFullName();
                    } catch (Exception e) {
                        currentEditorName = "Usuário desconhecido";
                    }
                } else {
                    hasControl = true; // Se não há lock, considera que tem controle
                }

                jsonObject.put("success", true);
                jsonObject.put("hasControl", hasControl);
                jsonObject.put("articleId", articleId);
                jsonObject.put("userId", themeDisplay.getUserId());
                jsonObject.put("lockExists", currentLock != null);

                if (currentLock != null) {
                    jsonObject.put("currentEditorId", currentEditorId);
                    jsonObject.put("currentEditor", currentEditorName);
                    jsonObject.put("lockCreated", currentLock.getCreateDate().toString());
                }
            } else {
                jsonObject.put("success", false);
                jsonObject.put("message", "Article ID is required");
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        resourceResponse.setContentType("application/json");
        PrintWriter writer = resourceResponse.getWriter();
        writer.write(jsonObject.toString());
        writer.flush();
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}