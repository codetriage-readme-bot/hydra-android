package be.ugent.zeus.hydra.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.MinervaDatabase;
import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaDao;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.AnnouncementDao;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseDao;
import be.ugent.zeus.hydra.data.database.minerva2.migrations.Migration_6_7;
import be.ugent.zeus.hydra.data.database.minerva2.migrations.Migration_7_8;
import be.ugent.zeus.hydra.data.database.minerva2.migrations.Migration_8_9;
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
        return Room.databaseBuilder(context, MinervaDatabase.class, MinervaDatabase.NAME)
                .allowMainThreadQueries()
                .addMigrations(new Migration_6_7(context), new Migration_7_8(), new Migration_8_9())
                .build();
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