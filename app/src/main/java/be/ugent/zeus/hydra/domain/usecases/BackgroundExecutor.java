package be.ugent.zeus.hydra.domain.usecases;

import java8.util.function.Function;

/**
 * Can be used to execute things on a background thread.
 *
 * @author Niko Strijbol
 */
public interface BackgroundExecutor {

    /**
     * Execute a runnable in the background.
     *
     * @param runnable The runnable to execute.
     * @param callback The callback, will be called after execution and on the main thread.
     * @param <R>      Teh type.
     */
    <R> void execute(Function<TaskControl<R>, R> runnable, Callback<R> callback);

    interface Callback<R> {

        void onCompleted(R result);

        void onProgress(R progress);
    }

    abstract class SimpleCallback<R> implements Callback<R> {

        @Override
        public void onProgress(R progress) {
            // Nothing.
        }
    }

    interface TaskControl<V> {
        void publishProgress(V progress);
    }
}