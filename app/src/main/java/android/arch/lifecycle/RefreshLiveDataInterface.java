package android.arch.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author Niko Strijbol
 */
public abstract class RefreshLiveDataInterface<D> extends LiveData<D> {

    /**
     * Request a refresh of the data. This method does not guarantee anything: it simple let's the LiveData know
     * that now is a good time to refresh.
     *
     * You can use {@link #registerDataLoadListener(OnDataLoadListener)} to observer when a refresh is starting.
     */
    public abstract void requestRefresh();

    /**
     * Request a refresh of the data. This method does not guarantee anything: it simple let's the LiveData know
     * that now is a good time to refresh.
     *
     * You can use {@link #registerDataLoadListener(OnDataLoadListener)} to observer when a refresh is starting.
     */
    public abstract void requestRefresh(Bundle args);

    /**
     * Register a listener. When called, existing listeners are discarded.
     *
     * @param listener The listener to register. If null, it unregisters any listener.
     */
    public abstract void registerDataLoadListener(@Nullable OnDataLoadListener listener);

    @FunctionalInterface
    public interface OnDataLoadListener {

        /**
         * Is called when the LiveData begins loading data.
         */
        void onDataLoadStart();
    }
}
