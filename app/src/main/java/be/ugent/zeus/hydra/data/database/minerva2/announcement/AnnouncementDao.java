package be.ugent.zeus.hydra.data.database.minerva2.announcement;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import be.ugent.zeus.hydra.data.database.minerva.AnnouncementTable;
import be.ugent.zeus.hydra.data.database.minerva.CourseTable;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;

import java.util.Collection;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@Dao
public interface AnnouncementDao {

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AnnouncementTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AnnouncementTable.Columns.COURSE + " = c." + CourseTable.Columns.ID + " WHERE a." + AnnouncementTable.Columns.ID + " IS :id")
    LiveData<Result> getOneLive(int id);

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AnnouncementTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AnnouncementTable.Columns.COURSE + " = c." + CourseTable.Columns.ID + " WHERE a." + AnnouncementTable.Columns.ID + " IS :id")
    Result getOne(int id);

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AnnouncementTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AnnouncementTable.Columns.COURSE + " = c." + CourseTable.Columns.ID)
    LiveData<List<Result>> getAllLive();

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AnnouncementTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AnnouncementTable.Columns.COURSE + " = c." + CourseTable.Columns.ID)
    List<Result> getAll();

    @Insert
    void insert(Announcement announcement);

    @Insert
    void insert(Collection<Announcement> announcements);

    @Update
    void update(Announcement announcement);

    @Update
    void update(Collection<Announcement> announcements);

    @Delete
    void delete(Announcement announcement);

    @Delete
    void delete(Collection<Announcement> announcements);

    @Query("DELETE FROM " + AnnouncementTable.TABLE_NAME + " WHERE " + AnnouncementTable.Columns.ID + " IN (:ids)")
    void deleteById(List<Integer> ids);

    @Query("DELETE FROM " + AnnouncementTable.TABLE_NAME + " WHERE " + AnnouncementTable.Columns.ID + " IS :id")
    void delete(int id);

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AnnouncementTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AnnouncementTable.Columns.COURSE + " = c." + CourseTable.Columns.ID + " WHERE a." + AnnouncementTable.Columns.READ_DATE + " = -1 ORDER BY a." + AnnouncementTable.Columns.DATE + " DESC")
    LiveData<List<Result>> getLiveUnreadMostRecentFirst();

    @Query("SELECT a.*, c." + CourseTable.Columns.ID + " AS c_" + CourseTable.Columns.ID +
            ", c." + CourseTable.Columns.CODE + " AS c_" + CourseTable.Columns.CODE +
            ", c." + CourseTable.Columns.TITLE + " AS c_" + CourseTable.Columns.TITLE +
            ", c." + CourseTable.Columns.DESCRIPTION + " AS c_" + CourseTable.Columns.DESCRIPTION +
            ", c." + CourseTable.Columns.TUTOR + " AS c_" + CourseTable.Columns.TUTOR +
            ", c." + CourseTable.Columns.ACADEMIC_YEAR + " AS c_" + CourseTable.Columns.ACADEMIC_YEAR +
            ", c." + CourseTable.Columns.ORDER + " AS c_" + CourseTable.Columns.ORDER +
            " FROM " + AnnouncementTable.TABLE_NAME + " a LEFT JOIN " + CourseTable.TABLE_NAME + " c ON a." + AnnouncementTable.Columns.COURSE + " = c." + CourseTable.Columns.ID + " WHERE a." + AnnouncementTable.Columns.READ_DATE + " = -1 ORDER BY a." + AnnouncementTable.Columns.DATE + " DESC")
    List<Result> getUnreadMostRecentFirst();

    class Result {
        @Embedded
        public Announcement announcement;
        @Embedded(prefix = "c_")
        public Course course;
    }
}
