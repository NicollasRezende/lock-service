package com.example.article.lock.service.wrapper;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service Wrapper para interceptar salvamento de artigos e liberar locks automaticamente
 */
@Component(
        immediate = true,
        property = {
                "service.ranking:Integer=10000"  // Ranking bem alto para garantir prioridade
        },
        service = ServiceWrapper.class
)
public class JournalArticleLocalServiceLockWrapper extends JournalArticleLocalServiceWrapper {

    private static final Log _log = LogFactoryUtil.getLog(JournalArticleLocalServiceLockWrapper.class);

    public JournalArticleLocalServiceLockWrapper() {
        super(null);
        _log.warn(">>> ARTICLE LOCK WRAPPER INITIALIZED - Ranking 10000 <<<");
    }

    // Intercepta updateStatus com long (mais comum em publicação)
    @Override
    public JournalArticle updateStatus(
            long userId, long classPK, int status,
            Map<String, Serializable> workflowContext,
            ServiceContext serviceContext) throws PortalException {

        _log.warn("=== WRAPPER: updateStatus called ===");
        _log.warn("ClassPK: " + classPK);
        _log.warn("Status: " + status + " (0=Approved, 8=Inactive)");
        _log.warn("UserId: " + userId);

        // Chama o método original
        JournalArticle article = super.updateStatus(
                userId, classPK, status, workflowContext, serviceContext);

        // Se está publicando (status aprovado)
        if (status == WorkflowConstants.STATUS_APPROVED) {
            _log.warn(">>> Article PUBLISHED! Releasing lock for: " + article.getArticleId());
            releaseLockForArticle(article.getArticleId());
        }

        return article;
    }

    // Intercepta updateArticle simples
    @Override
    public JournalArticle updateArticle(
            long userId, long groupId, long folderId, String articleId,
            double version, String content, ServiceContext serviceContext)
            throws PortalException {

        _log.warn("=== WRAPPER: updateArticle (simple) called ===");
        _log.warn("ArticleId: " + articleId);
        _log.warn("Saving article...");

        JournalArticle article = super.updateArticle(
                userId, groupId, folderId, articleId, version, content, serviceContext);

        _log.warn(">>> Article SAVED! Releasing lock for: " + articleId);
        releaseLockForArticle(articleId);

        return article;
    }

    // Intercepta updateArticle com títulos
    @Override
    public JournalArticle updateArticle(
            long userId, long groupId, long folderId, String articleId,
            double version, Map<Locale, String> titleMap,
            Map<Locale, String> descriptionMap, String content,
            String layoutUuid, ServiceContext serviceContext)
            throws PortalException {

        _log.warn("=== WRAPPER: updateArticle (with title) called ===");
        _log.warn("ArticleId: " + articleId);
        _log.warn("Saving article with titles...");

        JournalArticle article = super.updateArticle(
                userId, groupId, folderId, articleId, version, titleMap,
                descriptionMap, content, layoutUuid, serviceContext);

        _log.warn(">>> Article SAVED! Releasing lock for: " + articleId);
        releaseLockForArticle(articleId);

        return article;
    }

    // Intercepta qualquer update genérico
    @Override
    public JournalArticle updateJournalArticle(JournalArticle journalArticle) {
        _log.warn("=== WRAPPER: updateJournalArticle called ===");
        _log.warn("ArticleId: " + journalArticle.getArticleId());

        JournalArticle result = super.updateJournalArticle(journalArticle);

        _log.warn(">>> Article UPDATED! Releasing lock for: " + result.getArticleId());
        releaseLockForArticle(result.getArticleId());

        return result;
    }

    private void releaseLockForArticle(String articleId) {
        try {
            if (articleId != null && !articleId.isEmpty()) {
                _log.warn(">>> RELEASING LOCK for article: " + articleId);
                _articleEditLockLocalService.unlockArticle(articleId);
                _log.warn(">>> LOCK SUCCESSFULLY RELEASED for article: " + articleId);
            }
        } catch (Exception e) {
            _log.error(">>> ERROR releasing lock for article: " + articleId, e);
            e.printStackTrace();
        }
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}