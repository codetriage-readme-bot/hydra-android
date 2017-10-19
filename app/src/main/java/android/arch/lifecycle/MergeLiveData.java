package android.arch.lifecycle;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.Log;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.BiFunction;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Niko Strijbol
 */
public class MergeLiveData<T> extends BaseLiveData<T> {

    private static final String TAG = "MergeLiveData";

    private Map<LiveData<?>, Source<?>> sources = new HashMap<>();

    private ArrayDeque<Update<?>> scheduled = new ArrayDeque<>();
    private final Object lock = new Object();

    public MergeLiveData(Executor executor) {
        super(executor);
    }

    public MergeLiveData(Executor executor, boolean shouldLoadNow) {
        super(executor, shouldLoadNow);
    }

    @MainThread
    public <V> void addSource(LiveData<V> source, BiFunction<T, V, T> mergeFunction) {
        ExistingObserver<V> newObserver = new ExistingObserver<>(mergeFunction);
        Source<V> e = new Source<>(source, newObserver);
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
    public void removeSource(SingleLiveData<?> toRemote) {
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

    @Override
    protected void produceData(Bundle args) {
        for (Map.Entry<LiveData<?>, Source<?>> entry: sources.entrySet()) {
            if (entry.getKey() instanceof LiveDataInterface) {
                ((LiveDataInterface) entry.getKey()).requestRefresh(args);
            }
        }
    }

    @Override
    public <E> LiveDataInterface<E> map(BiFunction<Executor.Companion, T, E> mapper) {
        throw new UnsupportedOperationException("You cannot map a merged live data at the moment.");
    }

    private class ExistingObserver<V> implements Observer<V> {

        private final BiFunction<T, V, T> function;

        private ExistingObserver(BiFunction<T, V, T> function) {
            this.function = function;
        }

        @Override
        public void onChanged(@Nullable V newValue) {
            postUpdate(new Update<>(function, newValue));
        }
    }

    @Override
    protected void setValue(T value) {
        super.setValue(value);
        synchronized (lock) {
            Log.d(TAG, "POPPING UPDATE, size is " + scheduled.size());
            scheduled.poll();
            if (!scheduled.isEmpty()) {
                Log.d(TAG, "SCHEDULING NEXT UPDATE");
                scheduled.peek().execute();
            }
        }
    }

    private class Update<V> {
        private final BiFunction<T, V, T> function;
        private final V newValue;

        private Update(BiFunction<T, V, T> function, V value) {
            this.function = function;
            this.newValue = value;
        }

        private void execute() {
            executor.execute(() -> {
                T computedValue = function.apply(getValue(), newValue);
                postValue(computedValue);
            });
        }
    }

    private void postUpdate(Update<?> update) {
        synchronized (lock) {
            Log.d(TAG, "EXECUTING UPDATE size is " + scheduled.size());
            scheduled.add(update);
            if (scheduled.size() == 1) {
                Log.d(TAG, "EXECUTING UPDATE");
                update.execute();
            }
        }
    }
}