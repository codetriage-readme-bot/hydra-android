package be.ugent.zeus.hydra.di;

import be.ugent.zeus.hydra.data.database.minerva2.agenda.DatabaseAgendaItemRepository;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.DatabaseAnnouncementRepository;
import be.ugent.zeus.hydra.data.database.minerva2.course.DatabaseCourseRepository;
import be.ugent.zeus.hydra.data.network.minerva.MinervaRequestApi;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AgendaItemRepository;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AnnouncementRepository;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.CourseRepository;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.MinervaApi;
import dagger.Binds;
import dagger.Module;

/**
 * Provide database implementations for some repositories.
 *
 * @author Niko Strijbol
 */
@Module
public interface DatabaseRepoModule {

    @Binds
    AnnouncementRepository announcementRepository(DatabaseAnnouncementRepository announcementRepository);

    @Binds
    CourseRepository courseRepository(DatabaseCourseRepository courseRepository);

    @Binds
    AgendaItemRepository agendaItemRepository(DatabaseAgendaItemRepository agendaItemRepository);

    @Binds
    MinervaApi minervaApi(MinervaRequestApi requestApi);
}
