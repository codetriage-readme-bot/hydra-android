package be.ugent.zeus.hydra.domain.entities.minerva;

import java8.util.Objects;
import org.threeten.bp.Year;

/**
 * Represents an academic year, but only in the strict sense: it only supports the year,
 * not the actual start and end date.
 *
 * An example would be year 2016-2017.
 *
 * This class uses {@link Year}, which uses ISO-8601. It might not be suitable
 * for historic years.
 *
 * @author Niko Strijbol
 */
public final class AcademicYear implements Comparable<AcademicYear> {

    private final Year startYear;

    public AcademicYear(Year startYear) {
        this.startYear = startYear;
    }

    public AcademicYear(int startYear) {
        this.startYear = Year.of(startYear);
    }

    public Year getStartYear() {
        return startYear;
    }

    public Year getEndYear() {
        return startYear.plusYears(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicYear that = (AcademicYear) o;
        return Objects.equals(startYear, that.startYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startYear);
    }

    @Override
    public int compareTo(AcademicYear academicYear) {
        return startYear.compareTo(academicYear.startYear);
    }

    /**
     * @return A string representation of the academic year. The format is not specified.
     */
    @Override
    public String toString() {
        return getStartYear().toString() + " - " + getEndYear().toString();
    }
}