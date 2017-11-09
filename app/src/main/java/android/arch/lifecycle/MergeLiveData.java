package android.arch.lifecycle;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link LiveData} that supports merging multiple sources, similar to {@link MediatorLiveData}.
 *
 * One difference is that this class implements and supports the methods from {@link RefreshLiveDataInterface}.
 *
 * @author Niko Strijbol
 */
public class MergeLiveData<D> extends RefreshLiveDataInterface<D> {

    private static final String TAG = "MergeLiveData";

    private Map<LiveData<?>, Source<?>> sources = new HashMap<>();

    private OnDataLoadListener listener = null;

    @MainThread
    public void addSource(LiveData<D> source) {
        Observer<D> newObserver = new ExistingObserver();
        Source<D> e = new Source<>(source, newObserver);
        Source<?> existing = sources.putIfAbsent(source, e);
        if (existing != null && existing.getObserver() != newObserver) {
            throw new IllegalArgumentException("This source was already added with a different observer");
        } else if (existing == null) {
            if (this.hasActiveObservers()) {
                e.plug();
            }
        }
    }

    @MainThread
    public void removeSource(LiveData<?> toRemote) {
        Source<?> source = sources.remove(toRemote);
        if (source != null) {
            source.unplug();
        }
    }

    @CallSuper
    protected void onActive() {
        for(Map.Entry<LiveData<?>, Source<?>> entry: sources.entrySet()) {
            entry.getValue().plug();
        }
        super.onActive();
    }

    @CallSuper
    protected void onInactive() {
        for(Map.Entry<LiveData<?>, Source<?>> entry: sources.entrySet()) {
            entry.getValue().unplug();
        }
        super.onInactive();
    }

    /**
     * Request a refresh. The method will propagate the request to all contained instances that
     * implement {@link RefreshLiveDataInterface}.
     * If a contained LiveData does not implement this class, the request will not be propagated.
     *
     * A registered {@link android.arch.lifecycle.RefreshLiveDataInterface.OnDataLoadListener} will be notified when
     * the the propagation begins, regardless if there are actually any LiveData.
     */
    @Override
    public void requestRefresh() {
        if (listener != null) {
            listener.onDataLoadStart();
        }

        for (LiveData<?> data: sources.keySet()) {
            if (data instanceof RefreshLiveDataInterface) {
                ((RefreshLiveDataInterface) data).requestRefresh();
            }
        }
    }

    /**
     * Same as {@link #requestRefresh()}, but with an argument.
     *
     * @param args The bundle to pass.
     */
    @Override
    public void requestRefresh(Bundle args) {
        if (listener != null) {
            listener.onDataLoadStart();
        }

        for (LiveData<?> data: sources.keySet()) {
            if (data instanceof RefreshLiveDataInterface) {
                ((RefreshLiveDataInterface) data).requestRefresh(args);
            }
        }
    }

    @Override
    public void registerDataLoadListener(@Nullable OnDataLoadListener listener) {
        this.listener = listener;
    }

    private class ExistingObserver implements Observer<D> {
        @Override
        public void onChanged(@Nullable D newValue) {
            postValue(newValue);
        }
    }
}