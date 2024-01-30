## Stateful scheduler

---
Schedule stateful jobs, which has execution state information.

Configuration example:

```java
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
```
