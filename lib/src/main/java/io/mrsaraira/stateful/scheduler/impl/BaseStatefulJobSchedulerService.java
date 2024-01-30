package io.mrsaraira.stateful.scheduler.impl;

import io.mrsaraira.stateful.scheduler.ExecutionState;
import io.mrsaraira.stateful.scheduler.HasState;
import io.mrsaraira.stateful.scheduler.StatefulScheduledFuture;
import io.mrsaraira.stateful.scheduler.StatefulTaskScheduler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Base implementation of {@link StatefulJobSchedulerService}.
 * {@inheritDoc}
 *
 * @author Takhsin Saraira
 */
@RequiredArgsConstructor
public class BaseStatefulJobSchedulerService<T extends NamedJob> implements StatefulJobSchedulerService<T> {

    protected final StatefulTaskScheduler taskScheduler;
    protected final Map<String, T> jobs;
    private final Logger logger;
    protected final Map<String, StatefulScheduledFuture<?>> scheduledJobs = new ConcurrentHashMap<>();

    @Override
    public StatefulScheduledFuture<?> scheduleJob(NamedJob namedJob, long delay) {
        return scheduleJob(namedJob.getName(), namedJob, delay);
    }

    @Override
    public StatefulScheduledFuture<?> scheduleJob(String jobName, Runnable command, long delay) {
        return scheduledJobs.compute(jobName, (taskName, scheduledFuture) -> {
            if (scheduledFuture == null || (scheduledFuture.isCancelled() && (ExecutionState.COMPLETED.equals(scheduledFuture.getState()) || ExecutionState.STOPPED.equals(scheduledFuture.getState())))) {
                var future = taskScheduler.scheduleWithFixedDelay(command, Duration.ofMillis(delay));
                logger.info("Job '{}' scheduled with delay {}", jobName, delay);
                return future;
            } else {
                return scheduledFuture;
            }
        });
    }

    @Override
    public Map<String, T> getAllJobs() {
        return Collections.unmodifiableMap(jobs);
    }

    @Override
    public Optional<StatefulScheduledFuture<?>> getStatefulScheduledFuture(String jobName) {
        return Optional.ofNullable(scheduledJobs.get(jobName));
    }

    @Override
    public boolean cancelJob(@Nonnull String name, boolean mayInterruptIfRunning) {
        var isCancelled = getStatefulScheduledFuture(name)
                .filter(Predicate.not(Future::isDone))
                .map(f -> f.cancel(mayInterruptIfRunning)).orElse(false);
        logger.info("Job '{}' cancelled: {}", name, isCancelled);
        return isCancelled;
    }

    /**
     * Get all named jobs information.
     *
     * @return list of jobs information
     */
    public List<NamedJobInfo> getJobsInfo() {
        return getAllJobs().keySet().stream()
                .map(jobName -> {
                    var future = getStatefulScheduledFuture(jobName);
                    var builder = NamedJobInfo.builder()
                            .name(jobName)
                            .executionState(future.map(HasState::getState).orElse(null))
                            .threadState(future.flatMap(f -> f.getExecutingThreadState().map(Enum::name)).orElse(null))
                            .isCancelled(future.map(Future::isCancelled).orElse(true));
                    future.filter(scheduledFuture ->
                                    !ExecutionState.STOPPED.equals(scheduledFuture.getState())
                                    && !(ExecutionState.COMPLETED.equals(scheduledFuture.getState()) && scheduledFuture.isCancelled())
                            )
                            .ifPresent(statefulScheduledFuture -> {
                                var delay = statefulScheduledFuture.getDelay(TimeUnit.MILLISECONDS);
                                if (delay > 0) {
                                    builder.nextRun(delay);
                                } else {
                                    builder.elapsedTime(Math.abs(delay));
                                }
                            });
                    return builder.build();
                })
                .collect(Collectors.toList());
    }

    protected boolean isScheduled(String jobName) {
        return scheduledJobs.containsKey(jobName);
    }

}
