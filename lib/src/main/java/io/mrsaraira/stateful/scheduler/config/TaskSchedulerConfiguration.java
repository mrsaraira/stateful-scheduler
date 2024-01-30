package io.jetsmarter.services.jetcron.application.job.config;

import io.jetsmarter.services.jetcron.application.job.scheduler.StatefulTaskScheduler;
import io.jetsmarter.services.jetcron.application.job.scheduler.core.StatefulTaskSchedulerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@RequiredArgsConstructor
@Configuration
public class TaskSchedulerConfiguration {

    @Bean
    @DependsOn(value = {"applicationDataSource", "orderDataSource"})
    public TaskScheduler threadPoolTaskScheduler() {
        var taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);

        return taskScheduler;
    }

    @Bean
    @DependsOn(value = {"applicationDataSource", "orderDataSource"})
    public StatefulTaskScheduler statefulTaskScheduler() {
        return new StatefulTaskSchedulerImpl<>(threadPoolTaskScheduler());
    }

}
