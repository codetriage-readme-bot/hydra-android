package be.ugent.zeus.hydra.domain.usecases;

/**
 * Executes {@link Runnable}s. How and when is up to the implementations.
 * <br/>
 * As it is likely there will multiple implementations of this interface, the name of common implementations are
 * specified here as well.
 *
 * @author Niko Strijbol
 */
@FunctionalInterface
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
}