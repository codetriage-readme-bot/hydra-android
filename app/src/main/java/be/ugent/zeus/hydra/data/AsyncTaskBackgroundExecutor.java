package be.ugent.zeus.hydra.data;

import android.os.AsyncTask;

import be.ugent.zeus.hydra.domain.usecases.BackgroundExecutor;
import java8.util.function.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Niko Strijbol
 */
@Singleton
public class AsyncTaskBackgroundExecutor implements BackgroundExecutor {

    @Inject
    public AsyncTaskBackgroundExecutor() {
        // Nothing.
    }

    @Override
    public <R> void execute(Function<TaskControl<R>, R> runnable, Callback<R> callback) {
        new AsyncTask<Void, R, R>() {
            @Override
            protected R doInBackground(Void... voids) {
                return runnable.apply(this::publishProgress);
            }

            @SafeVarargs
            @Override
            protected final void onProgressUpdate(R... values) {
                super.onProgressUpdate(values);
                callback.onProgress(values[0]);
            }

            @Override
            protected void onPostExecute(R r) {
                super.onPostExecute(r);
                callback.onCompleted(r);
            }
        }.execute();
    }
}
