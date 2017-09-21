package be.ugent.zeus.hydra.domain.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.domain.requests.Requests;
import be.ugent.zeus.hydra.domain.usecases.Executor;

/**
 * Internal LiveData. This is for use within the domain package only.
 *
 * @author Niko Strijbol
 */
public abstract class RefreshLiveDataImpl<D> extends RefreshLiveData<D> {

    protected final Executor executor;

    @Nullable
    private OnRefreshStartListener onRefreshStartListener;

    private Bundle scheduledRefresh;
    /**
     * Indicates that this is the first refresh. If so, we don't notify the refresh listeners.
     */
    private boolean isFirstRefresh = true;

    /**
     * Construct a Live Data.
     *
     * @param executor The executor determines on which thread the data is executed.
     */
    public RefreshLiveDataImpl(Executor executor) {
        this.executor = executor;
        // Schedule the initial loading.
        scheduledRefresh = Bundle.EMPTY;
    }

    /**
     * Flag this data for a refresh. If there are active observers, the data is reloaded immediately. If there are no
     * active observers, the data will be reloaded when the next active observer registers.
     * <p>
     * If there are no active observers, the {@code args} are saved and will be used when reloading the data at a later
     * point. This method will discard any args from previous calls to this method.
     */
    @Override
    public void requestRefresh() {
        Bundle newArgs = new Bundle();
        newArgs.putBoolean(Requests.IGNORE_CACHE, true);
        if (hasActiveObservers()) {
            loadData(newArgs);
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

    private void loadData(Bundle args) {
        executeInBackground(args);
        if (!isFirstRefresh && onRefreshStartListener != null) {
            onRefreshStartListener.onRefreshStart();
        }
        if (isFirstRefresh) {
            isFirstRefresh = false;
        }
    }

    protected abstract void executeInBackground(Bundle args);

    @Override
    public void registerRefreshListener(OnRefreshStartListener listener) {
        onRefreshStartListener = listener;
    }
}