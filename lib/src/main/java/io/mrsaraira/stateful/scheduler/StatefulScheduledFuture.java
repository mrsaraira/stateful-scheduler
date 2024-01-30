package io.mrsaraira.stateful.scheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * The StatefulScheduledFuture interface represents a scheduled future with additional state information.
 *
 * @param <V> the result type of the computation
 * @author Takhsin Saraira
 */
public interface StatefulScheduledFuture<V> extends ScheduledFuture<V>, HasState {

}
