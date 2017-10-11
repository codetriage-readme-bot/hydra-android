package be.ugent.zeus.hydra.domain.usecases.schamper;

import android.os.Bundle;

import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.requests.Request;
import be.ugent.zeus.hydra.domain.requests.Requests;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.utils.SimpleUseCase;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 * Get the list of Schamper articles.
 *
 * By default this use case is executed on a background thread.
 *
 * @author Niko Strijbol
 */
public class GetSchamperArticles extends SimpleUseCase<Void, Result<List<SchamperArticle>>> {

    private final Request<SchamperArticle[]> request;

    @Inject
    public GetSchamperArticles(Cache cache, @Named(Executor.BACKGROUND) Executor executor, Request.Cacheable<SchamperArticle[]> request) {
        super(executor);
        this.request = Requests.cache(cache, request);
    }

    @Override
    protected Result<List<SchamperArticle>> doCalculations(Executor.Companion companion, Bundle args) {
        return request.performRequest(args).map(Arrays::asList);
    }
}