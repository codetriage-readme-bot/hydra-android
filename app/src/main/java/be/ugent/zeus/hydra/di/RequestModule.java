package be.ugent.zeus.hydra.di;

import android.content.Context;

import be.ugent.zeus.hydra.data.cache.CacheManager;
import be.ugent.zeus.hydra.data.network.requests.SchamperArticlesRequest;
import be.ugent.zeus.hydra.data.network.requests.association.EventRequest;
import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.entities.event.Event;
import be.ugent.zeus.hydra.domain.requests.Request;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author Niko Strijbol
 */
@Module
public class RequestModule {

    // TODO: move this?
    @Provides
    @Singleton
    public Cache providesCache(Context context) {
        return CacheManager.create(context);
    }

    @Provides
    public Request.Cacheable<SchamperArticle[]> provideSchamperRequest() {
        return new SchamperArticlesRequest();
    }

    @Provides
    public Request.Cacheable<Event[]> provideEventRequest() {
        return new EventRequest();
    }
}