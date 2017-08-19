package be.ugent.zeus.hydra.data.database.minerva2.course;

import be.ugent.zeus.hydra.domain.minerva.AcademicYear;
import be.ugent.zeus.hydra.domain.minerva.Tutor;
import org.mapstruct.Mapper;

/**
 * Converts {@link Course} to {@link be.ugent.zeus.hydra.domain.minerva.Course}.
 *
 * @author Niko Strijbol
 */
@Mapper
public abstract class CourseMapper {

    public abstract be.ugent.zeus.hydra.domain.minerva.Course courseToCourse(Course course);

    public abstract Course courseToCourse(be.ugent.zeus.hydra.domain.minerva.Course course);

    public Tutor tutorFromString(String name) {
        return new Tutor(name);
    }

    public String stringFromTutor(Tutor tutor) {
        return tutor.getName();
    }

    public AcademicYear academicYearFromInt(int year) {
        return new AcademicYear(year);
    }

    public int intFromAcademicYear(AcademicYear year) {
        return year.getStartYear().getValue();
    }
}