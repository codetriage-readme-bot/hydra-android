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

    @Query("SELECT * FROM " + AnnouncementTable.TABLE_NAME + " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID + " WHERE " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.ID + " IS :id")
    LiveData<Result> getOneLive(int id);

    @Query("SELECT * FROM " + AnnouncementTable.TABLE_NAME + " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID + " WHERE " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.ID + " IS :id")
    Result getOne(int id);

    @Query("SELECT * FROM " + AnnouncementTable.TABLE_NAME + " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID)
    LiveData<List<Result>> getAllLive();

    @Query("SELECT * FROM " + AnnouncementTable.TABLE_NAME + " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID)
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

    @Query("SELECT * FROM " + AnnouncementTable.TABLE_NAME + " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID + " WHERE " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.READ_DATE + " = -1 ORDER BY " + AnnouncementTable.TABLE_NAME + "." +AnnouncementTable.Columns.DATE + " DESC")
    LiveData<List<Result>> getLiveUnreadMostRecentFirst();

    @Query("SELECT * FROM " + AnnouncementTable.TABLE_NAME + " LEFT JOIN " + CourseTable.TABLE_NAME + " ON " + AnnouncementTable.TABLE_NAME + "." + AnnouncementTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID + " WHERE " + AnnouncementTable.TABLE_NAME + "." +AnnouncementTable.Columns.READ_DATE + " = -1 ORDER BY " + AnnouncementTable.TABLE_NAME + "." +AnnouncementTable.Columns.DATE + " DESC")
    List<Result> getUnreadMostRecentFirst();

    class Result {
        @Embedded(prefix = AnnouncementTable.TABLE_NAME)
        public Announcement announcement;
        @Embedded
        public Course course;
    }
}
