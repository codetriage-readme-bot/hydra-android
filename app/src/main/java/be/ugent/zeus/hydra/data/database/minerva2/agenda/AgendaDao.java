package be.ugent.zeus.hydra.data.database.minerva2.agenda;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import be.ugent.zeus.hydra.data.database.minerva2.course.CourseTable;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;
import org.threeten.bp.ZonedDateTime;

import java.util.Collection;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@Dao
public interface AgendaDao {

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AgendaTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AgendaTable.Columns.COURSE + " = c." + CourseTable.Columns.ID + " WHERE a." + AgendaTable.Columns.ID + " IS :id")
    LiveData<Result> getOneLive(int id);

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AgendaTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AgendaTable.Columns.COURSE + " = c." + CourseTable.Columns.ID + " WHERE a." + AgendaTable.Columns.ID + " IS :id")
    Result getOne(int id);

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AgendaTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AgendaTable.Columns.COURSE + " = c." + CourseTable.Columns.ID)
    LiveData<List<Result>> getAllLive();

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AgendaTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AgendaTable.Columns.COURSE + " = c." + CourseTable.Columns.ID)
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

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AgendaTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AgendaTable.Columns.COURSE + " = c." + CourseTable.Columns.ID +
            " WHERE a." + AgendaTable.Columns.COURSE + " = :courseId" +
            " AND a." + AgendaTable.Columns.START_DATE + " >= :now" +
            " ORDER BY " + AgendaTable.Columns.START_DATE + " ASC"
    )
    LiveData<List<Result>> getAllFutureForCourse(String courseId, ZonedDateTime now);

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AgendaTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AgendaTable.Columns.COURSE + " = c." + CourseTable.Columns.ID +
            " WHERE a." + AgendaTable.Columns.COURSE + " = :courseId" +
            " ORDER BY " + AgendaTable.Columns.START_DATE + " ASC"
    )
    LiveData<List<Result>> getAllForCourse(String courseId);

    class Result {
        @Embedded
        public AgendaItem agendaItem;
        @Embedded(prefix = "c_")
        public Course course;
    }
}
