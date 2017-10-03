package be.ugent.zeus.hydra.data.network.minerva.course;

import be.ugent.zeus.hydra.data.database.minerva2.TutorMapper;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseUnread;
import be.ugent.zeus.hydra.domain.entities.minerva.AcademicYear;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Converts {@link Course} to {@link be.ugent.zeus.hydra.domain.entities.minerva.Course}.
 *
 * @author Niko Strijbol
 */
@Mapper(uses = {TutorMapper.class})
public abstract class CourseMapper {

    @Mappings({
            @Mapping(source = "tutorName", target = "tutor"),
            @Mapping(source = "academicYear", target = "year"),
            @Mapping(target = "order", ignore = true)
    })
    public abstract be.ugent.zeus.hydra.domain.entities.minerva.Course courseToCourse(Course course);

    @Mappings({
            @Mapping(source = "tutor", target = "tutorName"),
            @Mapping(source = "year", target = "academicYear")
    })
    public abstract Course courseToCourse(be.ugent.zeus.hydra.domain.entities.minerva.Course course);

    public AcademicYear academicYearFromInt(int year) {
        return new AcademicYear(year);
    }

    public int intFromAcademicYear(AcademicYear year) {
        return year.getStartYear().getValue();
    }

    public abstract CourseUnread convert(be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread courseUnread);

    public abstract be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread convert(CourseUnread courseUnread);
}