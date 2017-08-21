package be.ugent.zeus.hydra.data.database.minerva2.agenda;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import be.ugent.zeus.hydra.data.database.minerva.AgendaTable;
import be.ugent.zeus.hydra.data.database.minerva.CourseTable;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;

import java.util.Collection;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@Dao
public interface AgendaDao {

    @Query("SELECT * FROM " + AgendaTable.TABLE_NAME +
            " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AgendaTable.TABLE_NAME + "." + AgendaTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID +
            " WHERE " + AgendaTable.TABLE_NAME + "." + AgendaTable.Columns.ID + " IS :id"
    )
    LiveData<Result> getOneLive(int id);

    @Query("SELECT * FROM " + AgendaTable.TABLE_NAME +
            " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AgendaTable.TABLE_NAME + "." + AgendaTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID +
            " WHERE " + AgendaTable.TABLE_NAME + "." + AgendaTable.Columns.ID + " IS :id"
    )
    Result getOne(int id);

    @Query("SELECT * FROM " + AgendaTable.TABLE_NAME +
            " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AgendaTable.TABLE_NAME + "." + AgendaTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID
    )
    LiveData<List<Result>> getAllLive();

    @Query("SELECT * FROM " + AgendaTable.TABLE_NAME +
            " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AgendaTable.TABLE_NAME + "." + AgendaTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID
    )
    List<Result> getAll();

    @Insert
    void insert(AgendaItem item);

    @Insert
    void insert(Collection<AgendaItem> items);

    @Update
    void update(AgendaItem item);

    @Update
    void update(Collection<AgendaItem> items);

    @Delete
    void delete(AgendaItem item);

    @Delete
    void delete(Collection<AgendaItem> items);

    @Query("DELETE FROM " + AgendaTable.TABLE_NAME + " WHERE " + AgendaTable.Columns.ID + " IN (:ids)")
    void deleteById(List<Integer> ids);

    @Query("DELETE FROM " + AgendaTable.TABLE_NAME + " WHERE " + AgendaTable.Columns.ID + " IS :id")
    void delete(int id);

    class Result {
        public AgendaItem agendaItem;
        public Course course;
    }
}
