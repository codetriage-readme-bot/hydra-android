package be.ugent.zeus.hydra.data.database.minerva2.course;

import be.ugent.zeus.hydra.data.database.minerva2.TutorMapper;
import be.ugent.zeus.hydra.domain.minerva.AcademicYear;
import org.mapstruct.Mapper;

/**
 * Converts {@link Course} to {@link be.ugent.zeus.hydra.domain.minerva.Course}.
 *
 * @author Niko Strijbol
 */
@Mapper(uses = {TutorMapper.class})
public abstract class CourseMapper {

    public abstract be.ugent.zeus.hydra.domain.minerva.Course courseToCourse(Course course);

    public abstract Course courseToCourse(be.ugent.zeus.hydra.domain.minerva.Course course);

    public AcademicYear academicYearFromInt(int year) {
        return new AcademicYear(year);
    }

    public int intFromAcademicYear(AcademicYear year) {
        return year.getStartYear().getValue();
    }

    public abstract CourseUnread convert(be.ugent.zeus.hydra.domain.minerva.CourseUnread courseUnread);

    public abstract be.ugent.zeus.hydra.domain.minerva.CourseUnread convert(CourseUnread courseUnread);
}