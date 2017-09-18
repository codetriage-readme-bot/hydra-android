package be.ugent.zeus.hydra.domain.entities.minerva;

import be.ugent.zeus.hydra.testing.Utils;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Tests for {@link AgendaItem}.
 *
 * @author Niko Strijbol
 */
public class AgendaItemTest {

    @Test
    public void testEqualsAndHashCode() {
        Utils.defaultVerifier(AgendaItem.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withOnlyTheseFields("id")
                .verify();
    }
}