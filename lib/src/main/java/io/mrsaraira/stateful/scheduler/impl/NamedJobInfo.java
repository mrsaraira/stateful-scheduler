package io.mrsaraira.stateful.scheduler.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.mrsaraira.stateful.scheduler.ExecutionState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NamedJobInfo {

    private String name;
    private ExecutionState executionState;
    private String threadState;
    private boolean isCancelled;
    private Long nextRun;
    private Long elapsedTime;

}
