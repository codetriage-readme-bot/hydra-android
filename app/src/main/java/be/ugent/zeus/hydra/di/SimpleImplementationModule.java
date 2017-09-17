package be.ugent.zeus.hydra.di;

import be.ugent.zeus.hydra.data.AsyncTaskExecutor;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import dagger.Binds;
import dagger.Module;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author Niko Strijbol
 */
@Module
public abstract class SimpleImplementationModule {

    @Binds
    @Named(Executor.BACKGROUND)
    @Singleton
    public abstract Executor provideBackgroundExecutor(AsyncTaskExecutor executor);
}