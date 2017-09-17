package be.ugent.zeus.hydra.domain.usecases;

/**
 * Executes things on a specified thread.
 *
 * The default instance is configured using dagger and is an executor that will execute things on a background thread.
 * Some constants contain the name of default executors.
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
     * Execute a runnable on a thread. The thread is specified by implementing classes.
     *
     * @param runnable The runnable to execute.
     */
    void execute(Runnable runnable);
}