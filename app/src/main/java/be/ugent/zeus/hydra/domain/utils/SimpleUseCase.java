package be.ugent.zeus.hydra.domain.utils;

import android.arch.lifecycle.LiveDataInterface;
import android.arch.lifecycle.SingleLiveData;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.UseCase;

/**
 * A simple use case where the use case is also the LiveData. In most cases, this is enough.
 *
 * @author Niko Strijbol
 */
public abstract class SimpleUseCase<IN, OUT> extends SingleLiveData<OUT> implements UseCase<IN, LiveDataInterface<OUT>> {

    protected SimpleUseCase(Executor executor) {
        super(executor);
    }

    @Override
    public LiveDataInterface<OUT> execute(IN arguments) {
        return this;
    }
}