package be.ugent.zeus.hydra.library.details;

import be.ugent.zeus.hydra.library.details.OpeningHours;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static be.ugent.zeus.hydra.testing.Assert.assertSerialization;
import static be.ugent.zeus.hydra.testing.Utils.defaultVerifier;

/**
 * @author Niko Strijbol
 */
public class OpeningHoursTest {

    @Test
    public void serialize() {
        assertSerialization(OpeningHours.class);
    }

    @Test
    public void equalsAndHash() {
        defaultVerifier(OpeningHours.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}