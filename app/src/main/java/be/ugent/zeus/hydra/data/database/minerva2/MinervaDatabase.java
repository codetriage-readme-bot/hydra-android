package be.ugent.zeus.hydra.data.database.minerva2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva.DatabaseHelper;
import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaDao;
import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaItem;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.Announcement;
import be.ugent.zeus.hydra.data.database.minerva2.announcement.AnnouncementDao;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseDao;
import be.ugent.zeus.hydra.utils.TtbUtils;

/**
 * @author Niko Strijbol
 */
@Database(entities = {Course.class, AgendaItem.class, Announcement.class}, version = DatabaseHelper.VERSION)
@TypeConverters(TtbUtils.class)
public abstract class MinervaDatabase extends RoomDatabase {

    private static MinervaDatabase instance;

    public static synchronized MinervaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MinervaDatabase.class, DatabaseHelper.NAME)
                    .addMigrations(new Migration_8_9())
                    .build();
        }
        return instance;
    }

    public abstract CourseDao getCourseDao();

    public abstract AnnouncementDao getAnnouncementDao();

    public abstract AgendaDao getAgendaDao();

    // The initial migration
    private static class Migration_8_9 extends Migration {

        public Migration_8_9() {
            super(8, 9);
        }

        @Override
        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            // Nothing
            supportSQLiteDatabase.beginTransaction();
            supportSQLiteDatabase.execSQL("ALTER TABLE minerva_courses ALTER COLUMN academic_year columnType NOT NULL");
            supportSQLiteDatabase.execSQL("ALTER TABLE minerva_courses DROP COLUMN student");
            supportSQLiteDatabase.endTransaction();
        }
    }
}