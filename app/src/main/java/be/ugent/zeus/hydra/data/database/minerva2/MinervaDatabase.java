package be.ugent.zeus.hydra.data.database.minerva2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva.DatabaseHelper;
import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaItem;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseDao;
import be.ugent.zeus.hydra.utils.TtbUtils;

/**
 * @author Niko Strijbol
 */
@Database(entities = {Course.class, AgendaItem.class}, version = 1)
@TypeConverters(TtbUtils.class)
public abstract class MinervaDatabase extends RoomDatabase {

    private static MinervaDatabase instance;

    public static synchronized MinervaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MinervaDatabase.class, DatabaseHelper.NAME)
                    .build();
        }
        return instance;
    }

    public abstract CourseDao getCourseDao();
}