package com.example.article.lock.scheduler;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Your Name
 */
@Component(
        service = SchedulerJobConfiguration.class
)
public class ArticleEditLockCleanupScheduler
        implements SchedulerJobConfiguration {

    private static final Log _log = LogFactoryUtil.getLog(ArticleEditLockCleanupScheduler.class);

    @Override
    public TriggerConfiguration getTriggerConfiguration() {
        // Executa a cada 2 minutos para testes
        // Em produção, mude para um intervalo maior (ex: 30)
        // Cron expression: segundos minutos horas dia mês dia-da-semana ano(opcional)
        return TriggerConfiguration.createTriggerConfiguration("0 0/2 * * * ?");
    }

    @Override
    public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
        return () -> {
            _log.info("Starting ArticleEditLock cleanup job...");
            try {
                _articleEditLockLocalService.cleanExpiredLocks();
                _log.info("ArticleEditLock cleanup job completed successfully");
            } catch (Exception e) {
                _log.error("Error during ArticleEditLock cleanup job", e);
                throw e;
            }
        };
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}