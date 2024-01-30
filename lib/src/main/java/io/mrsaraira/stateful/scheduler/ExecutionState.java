package io.mrsaraira.stateful.scheduler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The ExecutionState enum represents the possible states of an execution.
 *
 * @author Takhsin Saraira
 */
@Getter
@RequiredArgsConstructor
public enum ExecutionState {

    NEW("New"),
    STARTED("Started"),
    CANCELLED("Cancelled"),
    STOPPED("Stopped"),
    COMPLETED("Completed");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
