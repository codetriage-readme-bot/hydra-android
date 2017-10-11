package be.ugent.zeus.hydra.domain.utils.livedata;

import java8.util.function.Function;

/**
 * Similar to a {@link be.ugent.zeus.hydra.domain.requests.Result}, but for a computation that is cancelable.
 *
 * @author Niko Strijbol
 */
public class Cancelable<D> {

    private final D data;
    private final boolean wasCancelled;

    private Cancelable(D data, boolean wasCancelled) {
        this.data = data;
        this.wasCancelled = wasCancelled;
    }

    public boolean wasCancelled() {
        return wasCancelled;
    }

    public D getData() {
        if (wasCancelled) {
            throw new IllegalStateException("The execution was cancelled.");
        }
        return data;
    }

    public static <D> Cancelable<D> cancelled() {
        return new Cancelable<>(null, true);
    }

    public static <D> Cancelable<D> completed(D data) {
        return new Cancelable<>(data, false);
    }

    public <E> Cancelable<E> map(Function<D, E> function) {
        if (wasCancelled) {
            return cancelled();
        } else {
            return completed(function.apply(getData()));
        }
    }
}
