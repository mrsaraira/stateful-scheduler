package io.mrsaraira.stateful.scheduler.impl;

import io.mrsaraira.stateful.scheduler.StatefulScheduledFuture;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

/**
 * This interface defines a contract for managing stateful jobs.
 *
 * @param <T> The type of Job
 * @author Takhsin Saraira
 */
public interface StatefulJobSchedulerService<T extends NamedJob> {

    /**
     * Schedules a job to be executed after a specified delay.
     *
     * @param namedJob named job to schedule
     * @param delay    the delay before executing the task
     * @return the future representing the scheduled task
     */
    default StatefulScheduledFuture<?> scheduleJob(NamedJob namedJob, long delay) {
        return scheduleJob(namedJob.getName(), namedJob, delay);
    }

    /**
     * Schedules a job to be executed after a specified delay.
     *
     * @param jobName the name of the job
     * @param command the task to execute
     * @param delay   the delay before executing the task
     * @return the future representing the scheduled task
     */
    StatefulScheduledFuture<?> scheduleJob(String jobName, Runnable command, long delay);

    /**
     * Returns a StatefulScheduledFuture associated with the given job name.
     *
     * @param jobName the name of the job
     * @return an Optional containing the stateful future if it exists, else an empty Optional
     */
    Optional<StatefulScheduledFuture<?>> getStatefulScheduledFuture(String jobName);

    /**
     * Cancels a job with the given name.
     *
     * @param jobName               the name of the job
     * @param mayInterruptIfRunning if true, the thread executing this task should be interrupted; otherwise, in-progress tasks are allowed to complete
     * @return true if the task was cancelled, false if the task could not be cancelled
     */
    boolean cancelJob(@Nonnull String jobName, boolean mayInterruptIfRunning);

    /**
     * Returns a map of all the jobs.
     *
     * @return a map where the keys are job names and the values are jobs
     */
    Map<String, T> getAllJobs();

}
