package io.mrsaraira.stateful.scheduler.core;

import io.mrsaraira.stateful.scheduler.HasState;
import io.mrsaraira.stateful.scheduler.StatefulScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import javax.annotation.Nonnull;
import java.util.concurrent.*;

/**
 * Implementation of {@link StatefulScheduledFuture}
 * {@inheritDoc}
 *
 * @param <V> the result type of the computation
 * @author Takhsin Saraira
 */
@RequiredArgsConstructor
final class StatefulScheduledFutureImpl<V> implements StatefulScheduledFuture<V> {

    private final ScheduledFuture<V> delegate;
    @Delegate(types = HasState.class)
    private final StatefulRunnableJob statefulTask;

    @Override
    public long getDelay(@Nonnull TimeUnit unit) {
        return delegate.getDelay(unit);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        statefulTask.setCancelledState();
        return delegate.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return delegate.isCancelled();
    }

    @Override
    public boolean isDone() {
        return delegate.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return delegate.get();
    }

    @Override
    public V get(long timeout, @Nonnull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.get(timeout, unit);
    }

    @Override
    public int compareTo(@Nonnull Delayed o) {
        return delegate.compareTo(o);
    }

}
