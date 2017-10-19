package android.arch.lifecycle;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.BiFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Niko Strijbol
 */
public class MergeLiveData<T> extends BaseLiveData<T> {

    private Map<LiveData<T>, Source<T>> sources = new HashMap<>();

    public MergeLiveData(Executor executor) {
        super(executor);
    }

    public MergeLiveData(Executor executor, boolean shouldLoadNow) {
        super(executor, shouldLoadNow);
    }

    @MainThread
    public void addSource(LiveData<T> source, BiFunction<T, T, T> mergeFunction) {
        ExistingObserver newObserver = new ExistingObserver(mergeFunction);
        Source<T> e = new Source<>(source, newObserver);
        Source<T> existing = sources.putIfAbsent(source, e);
        if (existing != null && existing.getObserver() != newObserver) {
            throw new IllegalArgumentException("This source was already added with a different observer");
        } else if (existing == null) {
            if (this.hasActiveObservers()) {
                e.plug();
            }
        }
    }

    @MainThread
    public void removeSource(SingleLiveData<T> toRemote) {
        Source<T> source = sources.remove(toRemote);
        if (source != null) {
            source.unplug();
        }
    }

    @CallSuper
    protected void onActive() {
        for(Map.Entry<LiveData<T>, Source<T>> entry: sources.entrySet()) {
            entry.getValue().plug();
        }
        super.onActive();
    }

    @CallSuper
    protected void onInactive() {
        for(Map.Entry<LiveData<T>, Source<T>> entry: sources.entrySet()) {
            entry.getValue().unplug();
        }
        super.onInactive();
    }

    @Override
    protected void produceData(Bundle args) {
        for (Map.Entry<LiveData<T>, Source<T>> entry: sources.entrySet()) {
            if (entry.getKey() instanceof LiveDataInterface) {
                ((LiveDataInterface) entry.getKey()).requestRefresh(args);
            }
        }
    }

    @Override
    public <E> LiveDataInterface<E> map(BiFunction<Executor.Companion, T, E> mapper) {
        throw new UnsupportedOperationException("You cannot map a merged live data at the moment.");
    }

    private class ExistingObserver implements Observer<T> {

        private final BiFunction<T, T, T> function;

        private ExistingObserver(BiFunction<T, T, T> function) {
            this.function = function;
        }

        @Override
        public void onChanged(@Nullable T newValue) {
            executor.execute(() -> {
                T computedValue = function.apply(getValue(), newValue);
                postValue(computedValue);
            });
        }
    }
}