package io.mrsaraira.stateful.scheduler.config;

import io.mrsaraira.stateful.scheduler.StatefulTaskScheduler;
import io.mrsaraira.stateful.scheduler.core.StatefulTaskSchedulerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskSchedulerConfiguration {

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        var taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);

        return taskScheduler;
    }

    @Bean
    public StatefulTaskScheduler statefulTaskScheduler() {
        return new StatefulTaskSchedulerImpl<>(threadPoolTaskScheduler());
    }

}
