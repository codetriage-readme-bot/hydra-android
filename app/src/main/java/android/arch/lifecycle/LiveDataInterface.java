package android.arch.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.BiFunction;
import java8.util.function.Function;

/**
 * A LiveData that exposes a way to users to request a refresh of the data manually. This is useful for common
 * patterns as pull-to-refresh, but can be anything really.
 *
 * Note that you should never make instances of this class yourself; you should get them from a
 * {@link be.ugent.zeus.hydra.domain.usecases.UseCase}. This class fulfills the role of a traditional interface.
 *
 * @author Niko Strijbol
 */
public abstract class LiveDataInterface<D> extends LiveData<D> {

    /**
     * Request a refresh of the data. This method does not guarantee anything: it simple let's the LiveData know
     * that now is a good time to refresh.
     *
     * You can use {@link #registerDataLoadListener(OnDataLoadListener)} to observer when a refresh is starting.
     */
    public abstract void requestRefresh();

    /**
     * Request a refresh of the data. This method does not guarantee anything: it simple let's the LiveData know
     * that now is a good time to refresh.
     *
     * You can use {@link #registerDataLoadListener(OnDataLoadListener)} to observer when a refresh is starting.
     */
    public abstract void requestRefresh(Bundle args);

    @FunctionalInterface
    public interface OnDataLoadListener {

        /**
         * Is called when the LiveData begins loading data.
         */
        void onDataLoadStart();
    }

    /**
     * Register a listener. When called, existing listeners are discarded.
     *
     * @param listener The listener to register. If null, it unregisters any listener.
     */
    public abstract void registerDataLoadListener(@Nullable OnDataLoadListener listener);

    /**
     * Apply a transformation to the results of this LiveData. This method will probably be more efficient than
     * applying a method from {@link Transformations}.
     *
     * The implementation defines the specifics regarding threading: the function may be applied on a background thread
     * or it may be applied on the main thread.
     *
     * @param function The function to apply.
     * @param <E> The new data type.
     *
     * @return The resulting of the mapping.
     */
    public <E> LiveDataInterface<E> map(Function<D, E> function) {
        return map((companion, d) -> function.apply(d));
    }

    /**
     * Apply a long running function to this live data. You should check {@link Executor.Companion#isCancelled()}
     * on a regular basis. If cancelled, the result will be ignored, so the function may return anything.
     *
     * If the implementation does not run the function on a separate thread, the companion will always return false.
     *
     * @param function The function to apply.
     * @param <E> The new data type.
     *
     * @return The resulting of the mapping.
     */
    public abstract <E> LiveDataInterface<E> map(BiFunction<Executor.Companion, D, E> function);
}