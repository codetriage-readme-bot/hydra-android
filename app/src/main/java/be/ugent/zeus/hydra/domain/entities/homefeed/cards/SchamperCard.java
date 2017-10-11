package be.ugent.zeus.hydra.domain.entities.homefeed.cards;

import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.entities.homefeed.PriorityUtils;
import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.SchamperArticleSource;
import org.threeten.bp.Duration;
import org.threeten.bp.ZonedDateTime;

import static be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard.CardType.SCHAMPER;

/**
 * Home card for {@link SchamperArticle}.
 *
 * @author Niko Strijbol
 * @author feliciaan
 */
public class SchamperCard extends HomeCard {

    private final SchamperArticle article;

    public SchamperCard(SchamperArticle article) {
        this.article = article;
    }

    public SchamperArticle getArticle() {
        return article;
    }

    @Override
    public int getPriority() {
        ZonedDateTime date = article.getPubDate();
        Duration duration = Duration.between(date, ZonedDateTime.now());
        // We only show the last month of schamper articles.
        return PriorityUtils.lerp((int) duration.toDays(), 0, (int) SchamperArticleSource.MAX_ARTICLE_AGE.toDays());
    }

    @Override
    public int getCardType() {
        return SCHAMPER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchamperCard that = (SchamperCard) o;
        return java8.util.Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return java8.util.Objects.hash(article);
    }
}