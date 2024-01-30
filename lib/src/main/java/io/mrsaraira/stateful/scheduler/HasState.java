package io.mrsaraira.stateful.scheduler;

import java.util.Optional;

/**
 * This interface represents objects that have a particular state associated with them.
 *
 * @author Takhsin Saraira
 */
public interface HasState {

    /**
     * Returns the current execution state of the object.
     */
    ExecutionState getState();

    /**
     * Returns the state of the executing thread if it exists.
     */
    Optional<Thread.State> getExecutingThreadState();

}
