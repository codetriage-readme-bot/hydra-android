package be.ugent.zeus.hydra.data.database.minerva2.agenda;

import be.ugent.zeus.hydra.data.database.minerva2.TutorMapper;
import be.ugent.zeus.hydra.domain.entities.minerva.AcademicYear;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author Niko Strijbol
 */
@Mapper(uses = {TutorMapper.class, AcademicYear.class})
public abstract class AgendaMapper {

    @Mapping(source = "course.id", target = "courseId")
    public abstract AgendaItem convert(be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem item);

    @Mappings({
            @Mapping(source = "courseInstance", target = "course"),
            @Mapping(source = "item.id", target = "id"),
            @Mapping(source = "item.title", target = "title")
    })
    public abstract be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem convert(AgendaItem item, Course courseInstance);
}