 ## Stateful scheduler


Schedule stateful jobs, which has execution state information.

Configuration example:

```java
@Configuration
class TaskSchedulerConfiguration {

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        var taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(8);
        taskScheduler.setAwaitTerminationSeconds(40);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return taskScheduler;
    }

    @Bean
    public StatefulTaskScheduler statefulTaskScheduler() {
        return new StatefulTaskSchedulerImpl<>(threadPoolTaskScheduler());
    }
    
}
```

Usage example: 

```java
@Component
class MyJob implements NamedJob {
    
    @Override
    public String getName(){
        return "MyJob";
    };
    
    @Override
    public void run() {
        // job's logic
    }
    
}

@Slf4j
@Service
class MyStatefulJobSchedulerServiceImpl extends BaseStatefulJobSchedulerService<NamedJob> {

    public MyStatefulJobSchedulerServiceImpl(StatefulTaskScheduler taskScheduler, Map<String, NamedJob> jobs, Logger logger) {
        super(taskScheduler, jobs, log);
    }

}

/*
 * Now just inject MyStatefulJobSchedulerServiceImpl or StatefulJobSchedulerService to operate on available NamedJob
 * in the scheduler, or register your own dynamically with a given name.
 * 
 * Available operations:
 *  + schedule job
 *  + cancel job
 *  + get all jobs (name-NamedJob)
 *  + get job by name
 *  + You can extend this list as you wish :)
 */
```