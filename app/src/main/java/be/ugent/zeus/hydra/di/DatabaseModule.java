package be.ugent.zeus.hydra.di;

import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.MinervaDatabase;
import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaDao;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.AnnouncementDao;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author Niko Strijbol
 */
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public static MinervaDatabase provideMinervaDatabase(Context context) {
        return MinervaDatabase.create(context);
    }

    @Provides
    @Singleton
    public static AgendaDao provideAgendaDao(MinervaDatabase database) {
        return database.getAgendaDao();
    }

    @Provides
    @Singleton
    public static CourseDao provideCourseDao(MinervaDatabase database) {
        return database.getCourseDao();
    }

    @Provides
    @Singleton
    public static AnnouncementDao provideAnnouncementDao(MinervaDatabase database) {
        return database.getAnnouncementDao();
    }
}