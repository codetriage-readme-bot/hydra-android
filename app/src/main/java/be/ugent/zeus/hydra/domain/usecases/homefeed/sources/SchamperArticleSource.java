package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveDataInterface;

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
 * Retrieve the articles to display in the home feed.
 *
 * @author Niko Strijbol
 */
public class SchamperArticleSource extends OptionalFeedSource {

    /**
     * The maximal age of an article. Articles older than this (published before today minus this duration) will not
     * be displayed.
     */
    public static final Duration MAX_ARTICLE_AGE = Duration.ofDays(30);

    private final GetSchamperArticles useCase;

    @Inject
    public SchamperArticleSource(GetSchamperArticles useCase) {
        this.useCase = useCase;
    }

    @Override
    protected LiveDataInterface<Result<List<HomeCard>>> getActualData(Args args) {
        return useCase.map(listResult -> listResult.map(articles -> {
            LocalDateTime oldestAllowedDate = LocalDateTime.now().minus(MAX_ARTICLE_AGE);
            return StreamSupport.stream(articles)
                    .filter(a -> a.getLocalPubDate().isAfter(oldestAllowedDate))
                    .map(SchamperCard::new)
                    .collect(Collectors.toList());
        }));
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.SCHAMPER;
    }
}