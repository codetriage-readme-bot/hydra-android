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
import be.ugent.zeus.hydra.data.database.minerva2.migrations.Migration_9_10;
import be.ugent.zeus.hydra.utils.TtbUtils;

import javax.inject.Singleton;

import static be.ugent.zeus.hydra.data.database.minerva2.MinervaDatabase.VERSION;

/**
 * The database for Minerva-related stuff.
 *
 * The database is implemented as a Room database. This class should be a singleton, as it is fairly expensive.
 *
 * @author Niko Strijbol
 */
@Singleton
@Database(entities = {Course.class, AgendaItem.class, Announcement.class}, version = VERSION)
@TypeConverters(TtbUtils.class)
public abstract class MinervaDatabase extends RoomDatabase {

    /**
     * The current name of the database. Should not change.
     */
    public static final String NAME = "minervaDatabase.db";
    /**
     * The current version of the database. When changing this value, you must provide a appropriate migration, or the
     * app will crash.
     */
    public static final int VERSION = 10;

    /**
     * Create a new instance of the database.
     *
     * @param context The context. Must be suitable for {@link Room#databaseBuilder(Context, Class, String)}.
     *
     * @return An instance of the database.
     */
    public static synchronized MinervaDatabase create(Context context) {
        return Room.databaseBuilder(context, MinervaDatabase.class, NAME)
                .allowMainThreadQueries()
                .addMigrations(new Migration_6_7(), new Migration_7_8(), new Migration_8_9(), new Migration_9_10())
                .build();
    }

    /**
     * Get an implementation of the course dao. This method is safe to call multiple times; only one instance will be
     * created and returned.
     *
     * @return Instance of the course dao.
     */
    public abstract CourseDao getCourseDao();

    /**
     * Get an implementation of the announcement dao. This method is safe to call multiple times; only one instance will
     * be created and returned.
     *
     * @return Instance of the announcement dao.
     */
    public abstract AnnouncementDao getAnnouncementDao();

    /**
     * Get an implementation of the calendar dao. This method is safe to call multiple times; only one instance will be
     * created and returned.
     *
     * @return Instance of the calendar dao.
     */
    public abstract AgendaDao getAgendaDao();
}