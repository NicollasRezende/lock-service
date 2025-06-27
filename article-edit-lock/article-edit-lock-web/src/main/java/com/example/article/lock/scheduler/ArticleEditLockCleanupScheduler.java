package com.example.article.lock.scheduler;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.petra.function.UnsafeRunnable;
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
            _articleEditLockLocalService.cleanExpiredLocks();
        };
    }

    @Reference
    private ArticleEditLockLocalService _articleEditLockLocalService;
}