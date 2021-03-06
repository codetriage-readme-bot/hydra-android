package be.ugent.zeus.hydra.library.list;

import be.ugent.zeus.hydra.library.list.LibraryList;
import be.ugent.zeus.hydra.testing.Utils;
import be.ugent.zeus.hydra.common.ModelTest;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * @author Niko Strijbol
 */
public class LibraryListTest extends ModelTest<LibraryList> {

    public LibraryListTest() {
        super(LibraryList.class);
    }

    @Test
    public void equalsAndHash() {
        Utils.defaultVerifier(LibraryList.class)
                .withOnlyTheseFields("name", "libraries")
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}