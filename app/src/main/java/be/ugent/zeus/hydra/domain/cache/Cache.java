package be.ugent.zeus.hydra.domain.cache;

import be.ugent.zeus.hydra.repository.requests.CacheableRequest;
import java8.util.Optional;

import java.io.Serializable;

/**
 * A cache. This is a map like data structure that holds keys and objects. While the keys must be strings, no
 * restriction is applied on the objects. The objects are thus heterogeneous.
 * <p>
 * This is a file cache for {@link CacheableRequest}s. This is not a cache for a non-determined amount of keys. Use
 * something like DiskLruCache for that.
 * <p>
 * The cache is not thread safe. You should make sure to not write using the same key from different threads at the same
 * time. Behavior in that scenario is undefined.
 * <p>
 * The cache duration of an object is specified at retrieval. This simplifies forcing evicting the cache.
 * <p>
 * If the cache duration is set to {@link #NEVER}, the request will not be cached at all. The duration of the cache has
 * a millisecond precision.
 * <p>
 * Note: using 0 as duration is undefined behavior. You should in general only use multiples of the available
 * constants.
 *
 * @author Niko Strijbol
 */
public interface Cache {

    /**
     * Special value that indicates this request should not be cached. While it might seem useless to include this in
     * a cache, it makes it easier to just put every request through the cache.
     */
    long NEVER = -1;
    long ONE_SECOND = 1000;
    long ONE_MINUTE = 60 * ONE_SECOND;
    long ONE_HOUR = 60 * ONE_MINUTE;
    long ONE_DAY = 24 * ONE_HOUR;
    long ONE_WEEK = 7 * ONE_DAY;

    /**
     * Delete the cache for a given key. For a key that was not cached, this method does nothing.
     *
     * @param key The key of the cache.
     *
     * @return True if the file was deleted (or there was no file).
     */
    boolean evict(String key);

    /**
     * Get the cache for a given key. The returned optional will only contain data if a valid cache has been found.
     * This means the cache is present, not expired and no errors occurred while reading it. In all other cases the
     * optional will be empty.
     *
     * @param cacheable The cacheable entity.
     *
     * @param <R> The type of data.
     *
     * @return Optionally the data if the cache was present, readable and not expired.
     */
    <R extends Serializable> Optional<R> get(Cacheable<R> cacheable);

    /**
     * Read the cache for a given key. The returned optional will contain data if readable cached data was found.  This
     * means the cache is present and not errors occurred while reading it.
     *
     * This method is basically {@link #get(Cacheable)}, but ignores the expiration date on the cache.
     *
     * @param cacheable The cacheable entity.
     *
     * @param <R> The type of data.
     *
     * @return Optionally the data if the cache was present, readable.
     */
    <R extends Serializable> Optional<R> read(Cacheable<R> cacheable);

    /**
     * Put an entry in to the cache. This method will save the data contained
     *
     * This method is fault-tolerant: all errors are ignored.
     *
     * @param cacheable The key of the entry.
     * @param data The data to save.
     * @param <R> The type of the data.
     */
    <R extends Serializable> void put(Cacheable<R> cacheable, R data);
}