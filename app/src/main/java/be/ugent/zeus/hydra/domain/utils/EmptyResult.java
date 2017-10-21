package be.ugent.zeus.hydra.domain.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataInterface;
import android.arch.lifecycle.SimpleWrapper;

import be.ugent.zeus.hydra.domain.requests.Result;

import java.util.Collections;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class EmptyResult<R> extends LiveData<R> {

    /**
     * Construct an empty instance that will pass empty data.
     *
     * @param emptyInstance The empty instance.
     */
    private EmptyResult(R emptyInstance) {
        postValue(emptyInstance);
    }

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
        return new EmptyResult<>(Collections.emptyList());
    }

    public static <E> LiveDataInterface<Result<List<E>>> emptyResultList() {
        return SimpleWrapper.from(new EmptyResult<>(Result.Builder.fromData(Collections.emptyList())));
    }
}