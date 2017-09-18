package be.ugent.zeus.hydra.domain.entities.minerva;

import android.os.Build;
import android.support.annotation.RequiresApi;

import be.ugent.zeus.hydra.testing.ComparableAsserts;
import be.ugent.zeus.hydra.testing.Utils;
import org.junit.Test;
import org.threeten.bp.Year;

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
    public void testNaturalOrder() {

        AcademicYear object = new AcademicYear(2012);
        AcademicYear smaller = new AcademicYear(2000);
        AcademicYear bigger = new AcademicYear(2150);
        AcademicYear equal = new AcademicYear(2012);

        ComparableAsserts.testComparator(object, smaller, equal, bigger);
    }

    @Test
    public void testEquivalentConstructors() {
        assertEquals(new AcademicYear(2012), new AcademicYear(Year.of(2012)));
    }

    @Test
    public void testToString() {
        Year start = Year.of(2012);
        String expected = start.toString() + " - " + start.plusYears(1).toString();
        AcademicYear academicYear = new AcademicYear(start);
        assertEquals(expected, academicYear.toString());
    }
}