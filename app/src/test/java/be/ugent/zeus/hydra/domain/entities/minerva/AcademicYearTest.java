package be.ugent.zeus.hydra.domain.entities.minerva;

import android.os.Build;
import android.support.annotation.RequiresApi;

import be.ugent.zeus.hydra.testing.Utils;
import org.junit.Test;
import org.threeten.bp.Year;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link AcademicYear}.
 *
 * @author Niko Strijbol
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class AcademicYearTest {

    @Test
    public void testEqualsAndHashCode() {
        Utils.defaultVerifier(AcademicYear.class)
                .verify();
    }

    @Test
    public void testRange() {
        AcademicYear instance1 = new AcademicYear(2012);
        assertEquals(instance1.getStartYear(), Year.of(2012));
        assertEquals(instance1.getEndYear(), Year.of(2013));
    }

    @Test
    public void testOrder() {
        // Get random years.
        List<AcademicYear> randomYears = Utils.generate(AcademicYear.class, 20)
                .collect(Collectors.toList());

        // Sort them.
        Collections.sort(randomYears);

        // Assert they are sorted correctly.

    }
}