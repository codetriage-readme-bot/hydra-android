package be.ugent.zeus.hydra.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author Niko Strijbol
 */
@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
}
