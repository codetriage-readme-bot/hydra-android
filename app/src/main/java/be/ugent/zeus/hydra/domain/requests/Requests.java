package be.ugent.zeus.hydra.domain.requests;

import android.os.Bundle;
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
     * Set this to true in the args to ignore the cache.
     */
    public static final String IGNORE_CACHE = "be.ugent.zeus.hydra.domain.requests.skip_cache";

    /**
     * Indicates if cached data should be used when a request fails.
     */
    public static final String USE_CACHE_ON_FAILURE = "be.ugent.hydra.domain.requests.use_cache_on_failure";

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
     * Cache a request. This will return a new request that will attempt to get the value from the given cache.
     *
     * If {@link #IGNORE_CACHE} is set to true, any existing cache will be considered expired.
     *
     * If there is no cache or the cache is expired or otherwise not usable, the given request will be executed to get
     * the data. If that request was executed successfully, the new data is saved in the cache and returned. If the
     * request fails to execute and {@link #USE_CACHE_ON_FAILURE} is true (the default), any usable cache will be
     * returned instead, even if the cache is expired. This makes sense for most use cases, as it allows network requests
     * to use cached data when offline, even if that data is expired.
     *
     * Note that if the request fails and {@link #USE_CACHE_ON_FAILURE} is true, the cache will be used ignoring
     * {@link #IGNORE_CACHE}.
     *
     * TODO: add diagram
     *
     * @param request The request.
     * @param <R>     The type of data.
     *
     * @return The new request.
     *
     * @see #USE_CACHE_ON_FAILURE
     * @see #IGNORE_CACHE
     */
    public static <D extends Serializable, R extends Cacheable<D> & Request<D>> Request<D> cache(Cache cache, R request) {
        return args -> {
            if (args != null && args.getBoolean(IGNORE_CACHE, false)) {
                // We ignore the cache, execute now.
                return executeAndSave(cache, request, args);
            } else {
                // Get it from the cache if possible.
                return cache.get(request)
                        .map(Result.Builder::fromData)
                        .orElseGet(() -> executeAndSave(cache, request, args));
            }
        };
    }

    /**
     * Execute a request and save the result in the cache. A result will only be saved if the request was completed
     * without errors.
     *
     * If the request fails and there is no data at all, the method will attempt to return cached data, even if that
     * data is expired. You should only call this method when it makes sense to display old data. If not, you can
     * set {@link #USE_CACHE_ON_FAILURE} to false in the arguments of the returned request to disable this.
     *
     * @param cache The cache to use.
     * @param request The request to execute.
     * @param <D> The data.
     * @param <R> The request.
     *
     * @return The result.
     *
     * @see #USE_CACHE_ON_FAILURE
     */
    private static <D extends Serializable, R extends Cacheable<D> & Request<D>> Result<D> executeAndSave(Cache cache, R request, Bundle args) {

        Result<D> executed = request.performRequest(args);

        // If there is cached data, and the request has no data, add the cached data.
        if (!executed.hasData() && args.getBoolean(USE_CACHE_ON_FAILURE, true)) {
            Log.w("CachedRequest", "Error while executing request, attempting to get cached data. ");

            // If there is cached data, return a new Result with the cached data, else the existing result.
            return cache.read(request)
                    .map(d -> executed.updateWith(Result.Builder.fromData(d)))
                    .orElse(executed);
        } else {
            // If there is no exception, save the value in the cache.
            if (!executed.hasException()) {
                cache.put(request, executed.getData());
            }
            return executed;
        }
    }
}