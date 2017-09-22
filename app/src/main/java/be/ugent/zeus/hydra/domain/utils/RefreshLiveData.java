package be.ugent.zeus.hydra.domain.utils;

import android.arch.lifecycle.LiveData;

import java8.util.function.Function;

/**
 * A LiveData that supports requesting a refresh of the data. In most cases, you should not expose a more detailed
 * LiveData subclass than this one.
 *
 * @author Niko Strijbol
 */
public abstract class RefreshLiveData<D> extends LiveData<D> {

    /**
     * Request a refresh of the data.
     */
    public abstract void requestRefresh();

    @FunctionalInterface
    public interface OnRefreshStartListener {

        /**
         * Starts when the refresh begins.
         */
        void onRefreshStart();
    }

    public abstract void registerRefreshListener(OnRefreshStartListener listener);

    public abstract <E> RefreshLiveData<E> map(Function<D, E> function);
}