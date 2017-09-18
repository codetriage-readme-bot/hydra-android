package be.ugent.zeus.hydra.di;

import android.content.Context;

import be.ugent.zeus.hydra.data.cache.CacheManager;
import be.ugent.zeus.hydra.domain.cache.Cache;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author Niko Strijbol
 */
@Module
public class RequestModule {

    @Provides
    @Singleton
    public Cache providesCache(Context context) {
        return CacheManager.create(context);
    }
}