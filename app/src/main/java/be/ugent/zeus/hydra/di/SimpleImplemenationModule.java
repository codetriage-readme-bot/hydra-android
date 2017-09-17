package be.ugent.zeus.hydra.di;

import be.ugent.zeus.hydra.data.AsyncTaskBackgroundExecutor;
import be.ugent.zeus.hydra.domain.usecases.BackgroundExecutor;
import dagger.Binds;
import dagger.Module;

/**
 * @author Niko Strijbol
 */
@Module
public abstract class SimpleImplemenationModule {

    @Binds
    public abstract BackgroundExecutor provideBackgroundExecutor(AsyncTaskBackgroundExecutor executor);
}
