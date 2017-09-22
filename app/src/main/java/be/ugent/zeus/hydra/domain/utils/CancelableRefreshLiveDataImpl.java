package be.ugent.zeus.hydra.domain.utils;

import android.os.Bundle;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.BiFunction;
import java8.util.function.Function;

/**
 * LiveData used when execution might take a longer time. When the LiveData enters inactive state, an attempt will be
 * made to stop any active computation of new values.
 *
 * @author Niko Strijbol
 */
public class CancelableRefreshLiveDataImpl<D> extends RefreshLiveDataImpl<D> {

    protected final BiFunction<Executor.Companion, Bundle, CancelableResult<D>> executable;

    private transient Executor.Cancelable executingOrDone;

    /**
     * Construct a Live Data.
     *
     * @param executor The executor determines on which thread the data is executed.
     * @param executable The code to execute.
     */
    public CancelableRefreshLiveDataImpl(Executor executor, BiFunction<Executor.Companion, Bundle, CancelableResult<D>> executable) {
        super(executor);
        this.executable = executable;
    }

    @Override
    protected void executeInBackground(Bundle args) {
        executingOrDone = executor.execute(companion -> {
            CancelableResult<D> result = executable.apply(companion, args);
            if (!result.wasCancelled()) {
                postValue(result.getData());
            }
        });
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (executingOrDone != null) {
            if (executingOrDone.cancel()) {
                // Since we stopped execution here, schedule refresh for next time.
                requestRefresh();
            }
            // We are done with this task.
            executingOrDone = null;
        }
    }

    @Override
    public <E> RefreshLiveData<E> map(Function<D, E> function) {
        return new CancelableRefreshLiveDataImpl<>(executor, (companion, bundle) -> executable.apply(companion, bundle).map(function));
    }

    public static class CancelableResult<D> {

        private final D data;
        private final boolean wasCancelled;

        private CancelableResult(D data, boolean wasCancelled) {
            this.data = data;
            this.wasCancelled = wasCancelled;
        }

        public boolean wasCancelled() {
            return wasCancelled;
        }

        public D getData() {
            if (wasCancelled) {
                throw new IllegalStateException("The execution was cancelled.");
            }
            return data;
        }

        public static <D> CancelableResult<D> cancelled() {
            return new CancelableResult<>(null, true);
        }

        public static <D> CancelableResult<D> completed(D data) {
            return new CancelableResult<>(data, false);
        }

        public <E> CancelableResult<E> map(Function<D, E> function) {
            if (wasCancelled) {
                return cancelled();
            } else {
                return completed(function.apply(getData()));
            }
        }
    }
}