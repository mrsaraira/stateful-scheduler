package io.mrsaraira.stateful.scheduler.core;

import io.mrsaraira.stateful.scheduler.ExecutionState;
import io.mrsaraira.stateful.scheduler.HasState;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of {@link StatefulRunnableJob}
 *
 * @author Takhsin Saraira
 */
@RequiredArgsConstructor
final class StatefulRunnableJob implements Runnable, HasState {

    private final Runnable delegate;
    private final AtomicReference<ExecutionState> state = new AtomicReference<>(ExecutionState.NEW);
    @Nullable
    private volatile Thread currentThread;

    @Override
    public ExecutionState getState() {
        return state.get();
    }

    void setCancelledState() {
        this.state.compareAndSet(ExecutionState.STARTED, ExecutionState.CANCELLED);
    }

    public Optional<Thread.State> getExecutingThreadState() {
        return Optional.ofNullable(currentThread).map(Thread::getState);
    }

    @Override
    public void run() {
        try {
            currentThread = Thread.currentThread();
            state.set(ExecutionState.STARTED);
            delegate.run();
        } finally {
            state.compareAndSet(ExecutionState.CANCELLED, ExecutionState.STOPPED);
            state.compareAndSet(ExecutionState.STARTED, ExecutionState.COMPLETED);
            currentThread = null;
        }
    }

}
