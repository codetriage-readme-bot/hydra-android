package be.ugent.zeus.hydra.common.request;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The basis interface for a request. A request is something that returns data, not unlike a AsyncTask, but without
 * any constraints. It is basically a replacement for Supplier<T> in Java 8.
 *
 * @author Niko Strijbol
 */
@FunctionalInterface
public interface Request<T> {

    /**
     * Perform the request.
     *
     * @param args The args for this request. This value is nullable because {@link Intent#getExtras()} is nullable.
     *
     * @return The data.
     */
    @NonNull
    Result<T> performRequest(@Nullable Bundle args);
}