package io.mrsaraira.stateful.scheduler.core;

import io.mrsaraira.stateful.scheduler.StatefulScheduledFuture;
import io.mrsaraira.stateful.scheduler.StatefulTaskScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Implementation of {@link StatefulTaskScheduler}
 *
 * @param <T> the result type of the computation
 * @author Takhsin Saraira
 */
@RequiredArgsConstructor
public final class StatefulTaskSchedulerImpl<T extends TaskScheduler> implements StatefulTaskScheduler {

    private final T delegate;

    @Override
    public StatefulScheduledFuture<?> schedule(@Nonnull Runnable task, @Nonnull Trigger trigger) {
        var scheduledRunnableTask = new StatefulRunnableJob(task);
        var scheduledFuture = delegate.schedule(scheduledRunnableTask, trigger);
        return new StatefulScheduledFutureImpl<>(scheduledFuture, scheduledRunnableTask);
    }

    @Override
    public StatefulScheduledFuture<?> schedule(Runnable task, Instant startTime) {
        var scheduledRunnableTask = new StatefulRunnableJob(task);
        var scheduledFuture = delegate.schedule(scheduledRunnableTask, startTime);
        return new StatefulScheduledFutureImpl<>(scheduledFuture, scheduledRunnableTask);
    }

    @Override
    public StatefulScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
        var scheduledRunnableTask = new StatefulRunnableJob(task);
        var scheduledFuture = delegate.scheduleAtFixedRate(scheduledRunnableTask, startTime, period);
        return new StatefulScheduledFutureImpl<>(scheduledFuture, scheduledRunnableTask);
    }

    @Override
    public StatefulScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
        var scheduledRunnableTask = new StatefulRunnableJob(task);
        var scheduledFuture = delegate.scheduleAtFixedRate(scheduledRunnableTask, period);
        return new StatefulScheduledFutureImpl<>(scheduledFuture, scheduledRunnableTask);
    }

    @Override
    public StatefulScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay) {
        var scheduledRunnableTask = new StatefulRunnableJob(task);
        var scheduledFuture = delegate.scheduleWithFixedDelay(scheduledRunnableTask, startTime, delay);
        return new StatefulScheduledFutureImpl<>(scheduledFuture, scheduledRunnableTask);
    }

    @Override
    public StatefulScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay) {
        var scheduledRunnableTask = new StatefulRunnableJob(task);
        var scheduledFuture = delegate.scheduleWithFixedDelay(scheduledRunnableTask, delay);
        return new StatefulScheduledFutureImpl<>(scheduledFuture, scheduledRunnableTask);
    }

}
