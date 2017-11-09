package android.arch.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.utils.LiveDataUtils;
import java8.util.function.BiFunction;
import java8.util.function.Function;

/**
 * @author Niko Strijbol
 */
public class SimpleWrapper<D> extends LiveDataInterface<D> implements Observer<D> {

    private final LiveData<D> wrapped;
    private int version = -1; // TODO: why is this needed?

    protected SimpleWrapper(LiveData<D> wrapped) {
        this.wrapped = wrapped;
    }

    public static <D> SimpleWrapper<D> from(LiveData<D> liveData) {
        return new SimpleWrapper<>(liveData);
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
        return new SimpleWrapper<>(Transformations.map(wrapped, d -> mapping.apply(() -> false, d)));
    }

    @Override
    public <T> LiveDataInterface<T> map(Function<D, T> mapping) {
        return new SimpleWrapper<>(Transformations.map(wrapped, mapping::apply));
    }

    public <T> LiveDataInterface<T> mapAsync(Executor executor, Function<D, T> mapping) {
        return new SimpleWrapper<>(LiveDataUtils.mapAsync(executor, wrapped, mapping));
    }

    @Override
    public void onChanged(@Nullable D o) {
        if (this.version != this.wrapped.getVersion()) {
            this.version = this.wrapped.getVersion();
            setValue(o);
        }
    }
}