package be.ugent.zeus.hydra.data.cache;

import android.content.Context;

import be.ugent.zeus.hydra.domain.cache.Cache;

import java.io.File;

/**
 * Provide access to the cache instance.
 *
 * @author Niko Strijbol
 */
public class CacheManager {

    /**
     * Get an instance of the default cache. This should be used somewhere as a singleton.
     *
     * @param context A context.
     *
     * @return The default cache.
     */
    public static Cache create(Context context) {
        File cacheDirectory = context.getCacheDir();
        CacheExecutor executor = new SerializableExecutor(cacheDirectory);
        return new GenericCache(executor);
    }
}