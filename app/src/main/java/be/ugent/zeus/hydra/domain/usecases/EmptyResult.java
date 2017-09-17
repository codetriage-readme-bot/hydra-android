package be.ugent.zeus.hydra.domain.usecases;

import android.arch.lifecycle.LiveData;

import java.util.Collections;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class EmptyResult<R> extends LiveData<R> {

    /**
     * Get a LiveData with an empty list as value. The empty list will be set using {@link LiveData#postValue(Object)},
     * so this method is thread safe.
     *
     * Every LiveData is a new instance. The list may be the same instance.
     *
     * @param <E> The type of data.
     *
     * @return A LiveData with an empty list.
     */
    public static <E> LiveData<List<E>> emptyList() {
        return new LiveData<List<E>>() {
            {
                postValue(Collections.emptyList());
            }
        };
    }
}