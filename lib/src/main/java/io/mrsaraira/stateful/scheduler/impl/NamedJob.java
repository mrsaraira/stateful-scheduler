package io.mrsaraira.stateful.scheduler.impl;

import javax.annotation.Nonnull;

public interface NamedJob extends Runnable {

    @Nonnull
    String getName();

}
