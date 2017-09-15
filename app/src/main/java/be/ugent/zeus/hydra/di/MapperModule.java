package be.ugent.zeus.hydra.di;

import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaMapper;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.AnnouncementMapper;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseMapper;
import dagger.Module;
import dagger.Provides;
import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;

/**
 * @author Niko Strijbol
 */
@Module
public class MapperModule {

    @Provides
    @Singleton
    public CourseMapper provideCourseMapper() {
        return Mappers.getMapper(CourseMapper.class);
    }

    @Provides
    @Singleton
    public AnnouncementMapper provideAnnouncementMapper() {
        return Mappers.getMapper(AnnouncementMapper.class);
    }

    @Provides
    @Singleton
    public AgendaMapper provideAgendaMapper() {
        return Mappers.getMapper(AgendaMapper.class);
    }
}