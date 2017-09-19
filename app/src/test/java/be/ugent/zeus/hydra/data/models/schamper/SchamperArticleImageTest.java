package be.ugent.zeus.hydra.data.models.schamper;

import be.ugent.zeus.hydra.data.models.ModelTest;
import be.ugent.zeus.hydra.testing.Utils;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * @author Niko Strijbol
 */
public class SchamperArticleImageTest extends ModelTest<ArticleImage> {

    public SchamperArticleImageTest() {
        super(ArticleImage.class);
    }

    @Test
    public void equalsAndHash() {
        Utils.defaultVerifier(ArticleImage.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withOnlyTheseFields("url")
                .verify();
    }
}