package be.ugent.zeus.hydra.device;

import android.os.AsyncTask;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Executor that uses a background thread.
 *
 * @author Niko Strijbol
 */
@Singleton
public class AsyncTaskExecutor implements Executor {

    @Inject
    public AsyncTaskExecutor() {
        // Nothing to do here.
    }

    /**
     * This will execute the runnable using {@link AsyncTask#execute(Runnable)}.
     *
     * @param runnable The runnable to execute.
     */
    @Override
    public void execute(Runnable runnable) {
        AsyncTask.execute(runnable);
    }

    @Override
    public Cancelable execute(Consumer<Companion> companionConsumer) {
        return new Cancelable() {

            final SimpleTask task;

            {
                task = new SimpleTask(companionConsumer);
                task.execute();
            }

            @Override
            public boolean cancel() {
                return task.cancel(true);
            }
        };
    }

    private static class SimpleTask extends AsyncTask<Void, Void, Void> implements Companion {

        private final Consumer<Companion> toExecute;

        private SimpleTask(Consumer<Companion> toExecute) {
            this.toExecute = toExecute;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            toExecute.accept(this);
            return null;
        }
    }
}