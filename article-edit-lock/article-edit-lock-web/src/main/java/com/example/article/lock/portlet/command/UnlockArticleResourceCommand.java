package com.example.article.lock.portlet.command;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nicollas Rezende
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
                "mvc.command.name=/article_lock/unlock"
        },
        service = MVCResourceCommand.class
)
public class UnlockArticleResourceCommand extends BaseMVCResourceCommand {

    @Override
    protected void doServeResource(
            ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws Exception {

        String articleId = ParamUtil.getString(resourceRequest, "articleId");

        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

        try {
            if (articleId != null && !articleId.isEmpty()) {
                _articleEditLockLocalService.unlockArticle(articleId);
                jsonObject.put("success", true);
                jsonObject.put("message", "Article unlocked successfully");
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