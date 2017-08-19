package be.ugent.zeus.hydra.data.database.minerva2.course;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import be.ugent.zeus.hydra.data.database.minerva.CourseTable;

import java.util.Collection;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@Dao
public interface CourseDao {

    @Query("SELECT * FROM " + CourseTable.TABLE_NAME + " WHERE " + CourseTable.Columns.ID + " IS :id")
    LiveData<Course> getOneLive(String id);

    @Query("SELECT * FROM " + CourseTable.TABLE_NAME + " WHERE " + CourseTable.Columns.ID + " IS :id")
    Course getOne(String id);

    @Query("SELECT * FROM " + CourseTable.TABLE_NAME)
    LiveData<List<Course>> getAllLive();

    @Query("SELECT * FROM " + CourseTable.TABLE_NAME)
    List<Course> getAll();

    @Insert
    void insert(Course course);

    @Insert
    void insert(Collection<Course> courses);

    @Update
    void update(Course course);

    @Update
    void update(Collection<Course> courses);

    @Delete
    void delete(Course course);

    @Delete
    void delete(Collection<Course> courses);

    @Query("DELETE FROM " + CourseTable.TABLE_NAME + " WHERE " + CourseTable.Columns.ID + " IS :id")
    void deleteById(String id);
}