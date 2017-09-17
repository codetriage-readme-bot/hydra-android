package be.ugent.zeus.hydra.domain;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import be.ugent.zeus.hydra.domain.usecases.Executor;

/**
 * Contains utilities for {@link android.arch.lifecycle.LiveData}.
 *
 * @author Niko Strijbol
 */
public class LiveDataUtils {

    /**
     * Runs a mapping function on the thread specified by the {@link Executor#execute(Runnable)} method. The
     * returned LiveData may not be the same instance as the source LiveData.
     *
     * Due to limitations in the API of LiveData, the 'main' thread is still used: the source will produce an event
     * on the main thread, after which the transformation is launched on the specified thread. This launching happens
     * on the main thread, but should be fast enough.
     *
     * @param executor Used to execute on a (background) thread.
     * @param source   The source data.
     * @param function The applied to the source data.
     * @param <S>      The type of the source.
     * @param <R>      The type of the result.
     *
     * @return A LiveData with the mapped data from source.
     */
    public static <S, R> LiveData<R> mapAsync(Executor executor, LiveData<S> source, Function<S, R> function) {
        final MediatorLiveData<R> result = new MediatorLiveData<>();
        result.addSource(source, s -> executor.execute(() -> result.postValue(function.apply(s))));
        return result;
    }
}