package be.ugent.zeus.hydra.domain.usecases.schamper;

import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.requests.Request;
import be.ugent.zeus.hydra.domain.requests.Requests;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.utils.RefreshLiveData;
import be.ugent.zeus.hydra.domain.utils.RefreshLiveDataImpl;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class GetSchamperArticles implements UseCase<Void, RefreshLiveData<Result<List<SchamperArticle>>>> {

    private final Executor executor;
    private final Request<SchamperArticle[]> request;

    @Inject
    public GetSchamperArticles(Cache cache, @Named(Executor.BACKGROUND) Executor executor, Request.Cacheable<SchamperArticle[]> request) {
        this.executor = executor;
        this.request = Requests.cache(cache, request);
    }

    @Override
    public RefreshLiveData<Result<List<SchamperArticle>>> execute(Void ignored) {
        return new RefreshLiveDataImpl<>(executor, bundle -> request.performRequest(bundle).map(Arrays::asList));
    }
}