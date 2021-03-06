package be.ugent.zeus.hydra.minerva;

import be.ugent.zeus.hydra.minerva.announcement.Announcement;
import be.ugent.zeus.hydra.testing.Utils;
import be.ugent.zeus.hydra.common.ModelTest;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * @author Niko Strijbol
 */
public class AnnouncementTest extends ModelTest<Announcement> {

    public AnnouncementTest() {
        super(Announcement.class);
    }

    @Test
    public void equalsAndHash() {
        Utils.defaultVerifier(Announcement.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withOnlyTheseFields("itemId", "read")
                .verify();
    }
}