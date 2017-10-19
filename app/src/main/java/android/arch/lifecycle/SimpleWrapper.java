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

    public SimpleWrapper(LiveData<D> wrapped) {
        this.wrapped = wrapped;
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
    public <T> LiveDataInterface<T> map(BiFunction<Executor.Companion, D, T> mapper) {
        return new SimpleWrapper<>(Transformations.map(wrapped, d -> mapper.apply(() -> false, d)));
    }

    public <T> LiveDataInterface<T> mapAsync(Executor executor, Function<D, T> mapper) {
        return new SimpleWrapper<>(LiveDataUtils.mapAsync(executor, wrapped, mapper));
    }

    @Override
    public void onChanged(@Nullable D d) {
        setValue(d);
    }
}