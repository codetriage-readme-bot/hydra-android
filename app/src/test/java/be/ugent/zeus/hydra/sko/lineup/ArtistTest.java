package be.ugent.zeus.hydra.sko.lineup;

import be.ugent.zeus.hydra.common.ModelTest;
import be.ugent.zeus.hydra.sko.lineup.Artist;
import be.ugent.zeus.hydra.testing.Utils;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * @author Niko Strijbol
 */
public class ArtistTest extends ModelTest<Artist> {

    public ArtistTest() {
        super(Artist.class);
    }

    @Test
    public void equalsAndHash() {
        Utils.defaultVerifier(Artist.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withOnlyTheseFields("name", "stage", "start", "end")
                .verify();
    }
}