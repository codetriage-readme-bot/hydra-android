package android.arch.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.BiFunction;
import java8.util.function.Function;
import java8.util.function.Functions;

/**
 * @author Niko Strijbol
 */
public class SimpleWrapper<O, D> extends LiveDataInterface<D> implements Observer<O> {

    private final LiveData<O> wrapped;
    private final Function<O, D> mapper;
    private int version = -1; // TODO: why is this needed?

    protected SimpleWrapper(LiveData<O> wrapped, Function<O, D> function) {
        this.wrapped = wrapped;
        this.mapper = function;
    }

    public static <D> SimpleWrapper<D, D> from(LiveData<D> wrapped) {
        return new SimpleWrapper<>(wrapped, Functions.identity());
    }

    public static <O, D> SimpleWrapper<O, D> from(LiveData<O> wrapped, Function<O, D> mapper) {
        return new SimpleWrapper<>(wrapped, mapper);
    }

    @Override
    protected void onActive() {
        super.onActive();
        wrapped.observeForever(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        wrapped.removeObserver(this);
    }

    @Override
    public void requestRefresh() {
        requestRefresh(Bundle.EMPTY);
    }

    @Override
    public void requestRefresh(Bundle args) {
        D value = getValue();
        if (value != null) {
            setValue(value);
        }
    }

    @Override
    public void registerDataLoadListener(@Nullable OnDataLoadListener listener) {
        // This will not be triggered.
    }

    @Override
    public <T> LiveDataInterface<T> map(BiFunction<Executor.Companion, D, T> mapping) {
        return new SimpleWrapper<>(wrapped, o -> mapping.apply(() -> false, mapper.apply(o)));
    }

    @Override
    public <T> LiveDataInterface<T> map(Function<D, T> mapping) {
        return new SimpleWrapper<>(wrapped, Functions.andThen(mapper, mapping));
    }

    public <T> LiveDataInterface<T> mapAsync(Executor executor, Function<D, T> mapping) {
        return new SimpleWrapper<O, T>(wrapped, null) {
            @Override
            protected void applyData(O originalData) {
                executor.execute(() -> postValue(mapping.apply(mapper.apply(originalData))));
            }
        };
    }

    @Override
    public void onChanged(@Nullable O o) {
        if (this.version != this.wrapped.getVersion()) {
            this.version = this.wrapped.getVersion();
            applyData(o);
        }
    }

    protected void applyData(O originalData) {
        setValue(mapper.apply(originalData));
    }
}