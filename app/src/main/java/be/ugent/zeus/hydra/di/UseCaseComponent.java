package be.ugent.zeus.hydra.di;

import be.ugent.zeus.hydra.domain.usecases.minerva.ChangeCourseOrder;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetCoursesWithUnreadCount;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetUnreadAnnouncements;
import dagger.Component;

import javax.inject.Singleton;

/**
 * The component.
 *
 * @author Niko Strijbol
 */
@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class, DatabaseRepoModule.class, MapperModule.class})
public interface UseCaseComponent {

    GetUnreadAnnouncements getUnreadAnnouncement();
    GetCoursesWithUnreadCount getCoursesWithUnreadCount();
    ChangeCourseOrder changeCourseOrder();

}