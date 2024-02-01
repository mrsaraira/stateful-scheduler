 ## Stateful scheduler


Schedule stateful jobs, which contain additional execution state information.

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
 *  + schedule job (returns StatefulScheduledFuture)
 *  + cancel job
 *  + get jobs information (which includes its execution state and Thread.State)
 *  + get all jobs (name-NamedJob)
 *  + get job by name
 *  + You can extend this list as you wish :)
 */
```

### Execution states

![execution-states-transition.png](images/execution-states-transition.png)
Fig. 1 Execution State transition

* New - Job is scheduled but not yet running.
* Started - Job is currently running.
* Cancelled - The job has been cancelled and a and will not be rescheduled, but it still running.
* Stopped - The job fully stopped upon cancellation and may have been interrupted or completed normally.
* Completed - The job has completed and is no longer running.

