package be.ugent.zeus.hydra.data.cache;

import java8.util.Optional;

import java.io.Serializable;

/**
 * Performs the actual logic of saving, reading and deleting cache entries. This class does not handle cache-related
 * logic, it's purpose is to store/retrieve/delete entries, nothing more.
 * <p>
 * Since it might be expected that
 *
 * @author Niko Strijbol
 */
public interface CacheExecutor {

    /**
     * Save the data to disk. After calling this method, the data should be cached in such a way that it can be read
     * again by the same CacheExecutor implementation.
     *
     * @param key  The key for the cache.
     * @param data The data to cache.
     *
     * @throws CacheException If the data could not be saved.
     */
    <D extends Serializable> void save(String key, CacheObject<D> data) throws CacheException;

    /**
     * Read data from disk.
     *
     * @param key The key for the cache.
     *
     * @return An optional containing the data, if an entry for the given key exists. If there is no entry, the optional
     * is empty.
     *
     * @throws CacheException If there is no data or the data could not be read.
     */
    <D extends Serializable> Optional<CacheObject<D>> read(String key) throws CacheException;

    /**
     * Delete an entry.
     *
     * @param key The key for the entry to delete.
     *
     * @return True if the file was deleted, otherwise false.
     */
    boolean delete(String key);
}