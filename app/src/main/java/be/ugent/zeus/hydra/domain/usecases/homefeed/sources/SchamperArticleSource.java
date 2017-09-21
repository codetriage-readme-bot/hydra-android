package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.entities.homefeed.cards.SchamperCard;
import be.ugent.zeus.hydra.domain.requests.Request;
import be.ugent.zeus.hydra.domain.requests.Requests;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.homefeed.OptionalFeedSource;
import be.ugent.zeus.hydra.domain.utils.CancelableRefreshLiveDataImpl;
import java8.util.stream.Collectors;
import java8.util.stream.RefStreams;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * TODO: we currently do not use {@link be.ugent.zeus.hydra.domain.usecases.schamper.GetSchamperArticles} here, because
 * we have no way of mapping things on the same background thread.
 *
 * @author Niko Strijbol
 */
public class SchamperArticleSource extends OptionalFeedSource {

    public static final Duration MAX_ARTICLE_AGE = Duration.ofDays(30);

    private final Request<SchamperArticle[]> request;
    private final Executor executor;

    @Inject
    public SchamperArticleSource(Request.Cacheable<SchamperArticle[]> request, Cache cache, @Named(Executor.BACKGROUND) Executor executor) {
        this.executor = executor;
        // We cache the request.
        this.request = Requests.cache(cache, request);
    }

    @Override
    protected LiveData<Result<List<HomeCard>>> getActualData(Args args) {
        return new CancelableRefreshLiveDataImpl<>(executor, (companion, bundle) -> CancelableRefreshLiveDataImpl.CancelableResult.completed(request.performRequest(args.args).map(schamperArticles -> {
            LocalDateTime oldestAllowedData = LocalDateTime.now().minus(MAX_ARTICLE_AGE);
            return RefStreams.of(schamperArticles)
                    .filter(a -> a.getLocalPubDate().isAfter(oldestAllowedData))
                    .map(SchamperCard::new)
                    .collect(Collectors.toList());
        })));
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.SCHAMPER;
    }
}