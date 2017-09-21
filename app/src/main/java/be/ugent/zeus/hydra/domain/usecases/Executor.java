package be.ugent.zeus.hydra.domain.usecases;

import java8.util.function.Consumer;

/**
 * Executes {@link Runnable}s. How and when is up to the implementations.
 * <br/>
 * As it is likely there will multiple implementations of this interface, the name of common implementations are
 * specified here as well.
 *
 * @author Niko Strijbol
 */
public interface Executor {

    /**
     * Can be used as name with {@link javax.inject.Named} with dagger to get an executor that will execute things on
     * a background thread.
     */
    String BACKGROUND = "executor_background";

    /**
     * Execute a runnable on a thread. The thread is specified by implementing classes. The implementing class may
     * impose limitations and delays. The only guarantee is that the {@code runnable} will be executed eventually.
     * Similarly, there are no guarantees about what happens when this method is called multiple times. The only
     * guarantee is that every call will result in the {@code runnable} being executed, even if the same instance is
     * used for multiple calls.
     *
     * @param runnable The runnable to execute.
     */
    void execute(Runnable runnable);

    /**
     * Execute a function on a thread. This function is very similar to {@link #execute(Runnable)}, but provides more
     * control to the caller.
     *
     * The function to execute receives a {@link Companion} object to query if the request should be cancelled or not.
     * The function should periodically check this value and terminate early if possible.
     *
     * @param companionConsumer The function to execute.
     *
     * @return A cancelable task.
     */
    Cancelable execute(Consumer<Companion> companionConsumer);

    /**
     * Companion object for functions executed by the Executor.
     */
    @FunctionalInterface
    interface Companion {

        /**
         * @return True if this task should be cancelled.
         */
        boolean isCancelled();
    }

    @FunctionalInterface
    interface Cancelable {

        /**
         * Attempt to cancel the task. Note that the executing function itself is responsible for stopping execution.
         * As such there is no guarantee this will actually do anything.
         *
         * @return True if it was cancelled.
         */
        boolean cancel();
    }
}