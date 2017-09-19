package be.ugent.zeus.hydra.data.models.schamper;

import be.ugent.zeus.hydra.data.models.ModelTest;
import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.testing.Utils;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * @author Niko Strijbol
 */
public class SchamperArticleTest extends ModelTest<SchamperArticle> {

    public SchamperArticleTest() {
        super(SchamperArticle.class);
    }

    @Test
    public void equalsAndHash() {
        Utils.defaultVerifier(SchamperArticle.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withOnlyTheseFields("link", "pubDate")
                .verify();
    }
}