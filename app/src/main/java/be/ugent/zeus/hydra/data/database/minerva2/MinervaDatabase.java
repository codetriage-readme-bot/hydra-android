package be.ugent.zeus.hydra.data.database.minerva2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaDao;
import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaItem;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.Announcement;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.AnnouncementDao;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseDao;
import be.ugent.zeus.hydra.data.database.minerva2.migrations.Migration_6_7;
import be.ugent.zeus.hydra.data.database.minerva2.migrations.Migration_7_8;
import be.ugent.zeus.hydra.data.database.minerva2.migrations.Migration_8_9;
import be.ugent.zeus.hydra.utils.TtbUtils;

import javax.inject.Singleton;

import static be.ugent.zeus.hydra.data.database.minerva2.MinervaDatabase.VERSION;

/**
 * @author Niko Strijbol
 */
@Database(entities = {Course.class, AgendaItem.class, Announcement.class}, version = VERSION)
@TypeConverters(TtbUtils.class)
@Singleton
public abstract class MinervaDatabase extends RoomDatabase {

    public static final String NAME = "minervaDatabase.db";
    public static final int VERSION = 9;

    private static MinervaDatabase instance;

    public static synchronized MinervaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MinervaDatabase.class, NAME)
                    .allowMainThreadQueries()
                    .addMigrations(new Migration_6_7(context), new Migration_7_8(), new Migration_8_9())
                    .build();
        }
        return instance;
    }

    public abstract CourseDao getCourseDao();

    public abstract AnnouncementDao getAnnouncementDao();

    public abstract AgendaDao getAgendaDao();
}