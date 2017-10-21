package android.arch.lifecycle;

import android.os.Bundle;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.BiFunction;

/**
 * The base implementation for a LiveData. This class should only be used directly from the domain package.
 *
 * In addition to implementing the interface, it provides a way to specify on which thread calculations should happen,
 * by means of a {@link Executor}.
 *
 * @author Niko Strijbol
 */
public abstract class SingleLiveData<D> extends BaseLiveData<D> {

    /**
     * Construct a LiveData. This will scheduling an initial loading of the data.
     *
     * @param executor The executor determines on which thread the data is executed.
     */
    public SingleLiveData(Executor executor) {
        super(executor);
    }

    /**
     * Construct a LiveData. This will scheduling an initial loading of the data.
     *
     * @param executor The executor determines on which thread the data is executed.
     * @param shouldLoadNow Indicates if the data should begin loading immediately or not.
     */
    public SingleLiveData(Executor executor, boolean shouldLoadNow) {
        super(executor, shouldLoadNow);
    }

    protected void produceData(Bundle args) {
        this.executingOrDone = executor.execute(companion -> {
            D result = doCalculations(companion, args);
            if (!companion.isCancelled()) {
                postValue(result);
            }
        });
    }

    /**
     * Calculate or otherwise get the data. This function can be called on any thread, depending on which
     * executor is present.
     *
     * The results should be returned, not set using {@link #postValue(Object)} or {@link #setValue(Object)}.
     *
     * If the calculations was cancelled, the return value of this function is ignored, so it may return anything.
     *
     * @param companion If the calculation takes some time, this should be checked regularly to see check if the
     *                  calculations should be cancelled.
     * @param args The arguments.
     *
     * @return The result.
     */
    protected abstract D doCalculations(Executor.Companion companion, Bundle args);

    @Override
    public <E> SingleLiveData<E> map(BiFunction<Executor.Companion, D, E> function) {
        return new SingleLiveData<E>(executor, false) {
            @Override
            protected E doCalculations(Executor.Companion companion, Bundle args) {
                return function.apply(companion, SingleLiveData.this.doCalculations(companion, args));
            }
        };
    }
}