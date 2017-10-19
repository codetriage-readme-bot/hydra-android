package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveDataInterface;

import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.entities.homefeed.cards.SchamperCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.homefeed.OptionalFeedSource;
import be.ugent.zeus.hydra.domain.usecases.schamper.GetSchamperArticles;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class SchamperArticleSource extends OptionalFeedSource {

    public static final Duration MAX_ARTICLE_AGE = Duration.ofDays(30);

    private final GetSchamperArticles useCase;

    @Inject
    public SchamperArticleSource(GetSchamperArticles useCase) {
        this.useCase = useCase;
    }

    @Override
    protected LiveDataInterface<Result<List<HomeCard>>> getActualData(Args args) {
        useCase.requestRefresh(args.args);
        return useCase.map((companion, articles) -> articles.map(this::convertAndFilterResult));
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.SCHAMPER;
    }

    private List<HomeCard> convertAndFilterResult(List<SchamperArticle> articles) {
        LocalDateTime oldestAllowedData = LocalDateTime.now().minus(MAX_ARTICLE_AGE);
        return StreamSupport.stream(articles)
                .filter(a -> a.getLocalPubDate().isAfter(oldestAllowedData))
                .map(SchamperCard::new)
                .collect(Collectors.toList());
    }
}