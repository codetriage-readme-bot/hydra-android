package be.ugent.zeus.hydra.data.cache;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import be.ugent.zeus.hydra.BuildConfig;
import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.cache.Cacheable;
import java8.util.Optional;
import org.threeten.bp.Duration;

import java.io.Serializable;

/**
 * A simple cache that will use a provided {@link CacheExecutor} to save the cache.
 *
 * @author Niko Strijbol
 */
public class GenericCache implements Cache {

    private static final String TAG = "GenericCache";

    private final CacheExecutor executor;

    GenericCache(CacheExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean evict(String key) {
        return executor.delete(key);
    }

    @Override
    public <R extends Serializable> Optional<R> get(Cacheable<R> cacheable) {
        return readInternal(cacheable).flatMap(cacheObject -> {
            if (shouldRefresh(cacheObject, cacheable)) {
                Log.i(TAG, "Could not use cached response for request " + cacheable.getCacheKey());
                return Optional.empty();
            } else {
                Log.i(TAG, "Cached response for request " + cacheable.getCacheKey());
                return Optional.ofNullable(cacheObject.getData());
            }
        });
    }

    @Override
    public <R extends Serializable> void put(Cacheable<R> cacheable, R data) {
        CacheObject<R> newData = new CacheObject<>(data);
        try {
            executor.save(cacheable.getCacheKey(), newData);
        } catch (CacheException e) {
            Log.w(TAG, "Could not cache request " + cacheable.getCacheKey(), e);
        }
    }

    public <D extends Serializable> Optional<CacheObject<D>> readInternal(Cacheable<D> cacheable) {
        try {
            return executor.read(cacheable.getCacheKey());
        } catch (CacheException e) {
            Log.w(TAG, "Could not read cache for " + cacheable.getCacheKey(), e);
            return Optional.empty();
        }
    }

    @Override
    public <D extends Serializable> Optional<D> read(Cacheable<D> cacheable) {
        return readInternal(cacheable).map(CacheObject::getData);
    }

    /**
     * @return True if fresh data should be used, for various reasons.
     */
    @VisibleForTesting
    boolean shouldRefresh(@Nullable CacheObject<?> object, Cacheable<?> cacheable) {

        // The requested duration the entry should be cached.
        long duration = cacheable.getCacheDuration();

        // Note: these are separate if statements to improve readability.
        if (object == null) {
            // A non-existing object should be refreshed.
            return true;
        }

        if (duration == NEVER) {
            // The key should not be cached.
            return true;
        }

        if (object.isExpired(Duration.ofMillis(duration))) {
            // The object is expired.
            return true;
        }

        if (object.getVersion() != BuildConfig.VERSION_CODE) {
            // The cache is from an older app version, so dump it.
            return true;
        }

        // The cache is still valid.
        return false;
    }
}