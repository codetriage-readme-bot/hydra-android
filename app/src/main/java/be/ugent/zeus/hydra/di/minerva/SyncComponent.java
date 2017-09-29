package be.ugent.zeus.hydra.di.minerva;

import be.ugent.zeus.hydra.di.*;
import be.ugent.zeus.hydra.domain.usecases.minerva.sync.SyncCoursesUseCase;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Provide dependencies for sync adapters.
 *
 * @author Niko Strijbol
 */
@Singleton
@Component(modules = {AppModule.class, ImplementationModule.class, RequestModule.class, DatabaseRepoModule.class, DatabaseModule.class})
public interface SyncComponent {
    // Main sync object for courses.
    SyncCoursesUseCase syncCoursesUseCase();
}