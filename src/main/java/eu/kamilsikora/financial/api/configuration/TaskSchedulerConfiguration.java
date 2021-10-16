package eu.kamilsikora.financial.api.configuration;

import eu.kamilsikora.financial.api.service.AsyncSynchronizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class TaskSchedulerConfiguration {

    private final AsyncSynchronizationService synchronizationService;

    @PostConstruct
    public void scheduleTasks() {
        final ThreadPoolTaskScheduler taskScheduler = threadPoolTaskScheduler();
        taskScheduler.schedule(synchronizationService, new CronTrigger("0 0 * ? * *"));

    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

}
