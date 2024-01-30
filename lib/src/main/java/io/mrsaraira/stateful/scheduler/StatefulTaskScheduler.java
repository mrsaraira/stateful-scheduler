package io.mrsaraira.stateful.scheduler;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

import java.time.Duration;
import java.time.Instant;

/**
 * Scheduler which extends {@link TaskScheduler} and returns {@link StatefulScheduledFuture} for scheduled tasks.
 * {@inheritDoc}
 *
 * @author Takhsin Saraira
 */
public interface StatefulTaskScheduler extends TaskScheduler {

    @Override
    StatefulScheduledFuture<?> schedule(Runnable task, Trigger trigger);

    @Override
    StatefulScheduledFuture<?> schedule(Runnable task, Instant startTime);

    @Override
    StatefulScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period);

    @Override
    StatefulScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period);

    @Override
    StatefulScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay);

    @Override
    StatefulScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay);

}
