package be.ugent.zeus.hydra.domain.utils;

import android.os.Bundle;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.Function;

/**
 * Simple LiveData that executes simple operations.
 *
 * @author Niko Strijbol
 */
public class SimpleRefreshLiveDataImpl<D> extends RefreshLiveDataImpl<D> {

    private final Function<Bundle, D> executable;

    /**
     * Construct a Live Data.
     *
     * @param executor The executor determines on which thread the data is executed.
     * @param code     The function that will actually get the code.
     */
    public SimpleRefreshLiveDataImpl(Executor executor, Function<Bundle, D> code) {
        super(executor);
        this.executable = code;
    }

    @Override
    protected void executeInBackground(Bundle args) {
        executor.execute(() -> postValue(executable.apply(args)));
    }
}