package be.ugent.zeus.hydra.domain.requests;

import android.util.Log;

import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.cache.Cacheable;
import java8.util.function.Function;

import java.io.Serializable;

/**
 * Utility methods for use with {@link Request}s.
 *
 * @author Niko Strijbol
 */
public class Requests {

    /**
     * Transform a {@code request} to result in another value. This is similar to {@link Result#map(Function)}, but this
     * method allows transforming the request's result without executing the request now.
     *
     * @param request  The request to apply the function on.
     * @param function The function to apply.
     * @param <R>      The type of the result.
     * @param <O>      The type of the original request.
     *
     * @return The new request.
     */
    public static <O, R> Request<R> map(Request<O> request, Function<O, R> function) {
        return args -> request.performRequest(args).map(function);
    }

    /**
     * Cache a request.
     *
     * @param request The request.
     * @param <R>     The type of data.
     *
     * @return The new request.
     */
    public static <D extends Serializable, R extends Cacheable<D> & Request<D>> Result<D> performCachedRequest(Cache cache, R request) {
        return cache.get(request)
                .map(Result.Builder::fromData)
                .orElseGet(() -> {
                    Result<D> executed = request.performRequest(null);

                    // If there is cached data, and the request has no data, add the cached data.
                    if (!executed.hasData()) {
                        Log.w("CachedRequest", "Error while executing request, attempting to get cached data. ");

                        // If there is cached data, return a new Result with the cached data, else the existing result.
                        return cache.read(request)
                                .map(d -> executed.updateWith(Result.Builder.fromData(d)))
                                .orElse(executed);
                    } else {
                        return executed;
                    }
                });
    }
}