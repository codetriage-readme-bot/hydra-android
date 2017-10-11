package be.ugent.zeus.hydra.domain.utils.livedata;

import android.os.Bundle;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.domain.requests.Requests;
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
public abstract class BaseLiveData<D> extends LiveDataInterface<D> {

    /**
     * The executor, which determines on which thread the calculations will actually be performed.
     */
    protected final Executor executor;

    /**
     * An optional listener for when the LiveData begins to load data.
     */
    @Nullable
    private OnDataLoadListener onDataLoadListener;

    /**
     * Store a scheduled refresh request.
     */
    private Bundle scheduledRefresh;

    /**
     * Indicates that this is the first refresh. If so, we don't notify the refresh listeners.
     */
    private boolean isFirstRefresh = true;

    /**
     * Indicates the last request that was made or that is in progress.
     */
    protected transient Executor.Cancelable executingOrDone;

    /**
     * Construct a LiveData. This will scheduling an initial loading of the data.
     *
     * @param executor The executor determines on which thread the data is executed.
     */
    public BaseLiveData(Executor executor) {
        this(executor, true);
    }

    /**
     * Construct a LiveData. This will scheduling an initial loading of the data.
     *
     * @param executor The executor determines on which thread the data is executed.
     * @param shouldLoadNow Indicates if the data should begin loading immediately or not.
     */
    public BaseLiveData(Executor executor, boolean shouldLoadNow) {
        this.executor = executor;
        if (shouldLoadNow) {
            requestLoad();
        }
    }

    /**
     * Can be used to indicate the data should be loaded when the live data becomes active. Calling this when
     * the live data is already active is pointless. It should therefor be called before any observer has the chance
     * to register.
     *
     * This function's main purpose is to enable lazy loading of the data for subclass.
     */
    public void requestLoad() {
        scheduledRefresh = Bundle.EMPTY;
    }

    /**
     * Can be used to indicate the data should be loaded when the live data becomes active. Calling this when
     * the live data is already active is pointless. It should therefor be called before any observer has the chance
     * to register.
     *
     * This function's main purpose is to enable lazy loading of the data for subclass.
     */
    public void requestLoad(Bundle args) {
        scheduledRefresh = args;
    }

    @Override
    public void requestRefresh() {
        requestRefresh(Bundle.EMPTY);
    }

    @Override
    public void requestRefresh(Bundle args) {
        Bundle newArgs = new Bundle(args);
        // We want to ignore any existing data.
        newArgs.putBoolean(Requests.IGNORE_CACHE, true);

        if (hasActiveObservers()) {
            loadData(newArgs);
            this.scheduledRefresh = null;
        } else {
            this.scheduledRefresh = newArgs;
        }
    }

    @Override
    protected void onActive() {
        super.onActive();

        if (scheduledRefresh != null) {
            // Temp copy for execution.
            loadData(scheduledRefresh);
            scheduledRefresh = null;
        }
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

    /**
     * Schedule the data to be loaded.
     *
     * @param args The arguments.
     */
    private void loadData(Bundle args) {
        produceData(args);
        if (!isFirstRefresh && onDataLoadListener != null) {
            onDataLoadListener.onDataLoadStart();
        }
        if (isFirstRefresh) {
            isFirstRefresh = false;
        }
    }

    protected void produceData(Bundle args) {
        this.executingOrDone = executor.execute(companion -> {
            D result = doCalculations(companion, args);
            if (!companion.isCancelled()) {
                postValue(result);
            }
        });
    }

    @Override
    public void registerDataLoadListener(OnDataLoadListener listener) {
        onDataLoadListener = listener;
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
    public <E> LiveDataInterface<E> map(BiFunction<Executor.Companion, D, E> function) {
        return new BaseLiveData<E>(executor) {
            @Override
            protected E doCalculations(Executor.Companion companion, Bundle args) {
                return function.apply(companion, BaseLiveData.this.doCalculations(companion, args));
            }
        };
    }
}