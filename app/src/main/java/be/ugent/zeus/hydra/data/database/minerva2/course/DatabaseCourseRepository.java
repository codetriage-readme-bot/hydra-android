package be.ugent.zeus.hydra.data.database.minerva2.course;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.MinervaDatabase;
import be.ugent.zeus.hydra.domain.minerva.Course;
import be.ugent.zeus.hydra.domain.minerva.CourseRepository;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

import static be.ugent.zeus.hydra.utils.IterableUtils.transform;

/**
 * @author Niko Strijbol
 */
public class DatabaseCourseRepository implements CourseRepository {

    private final CourseDao courseDao;
    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    public DatabaseCourseRepository(Context context) {
        this.courseDao = MinervaDatabase.getInstance(context).getCourseDao();
    }

    @Override
    public LiveData<Course> getOneLive(String id) {
        return Transformations.map(courseDao.getOneLive(id), courseMapper::courseToCourse);
    }

    @Override
    public Course getOne(String id) {
        return courseMapper.courseToCourse(courseDao.getOne(id));
    }

    @Override
    public LiveData<List<Course>> getAllLive() {
        return Transformations.map(courseDao.getAllLive(), i -> transform(i, courseMapper::courseToCourse));
    }

    @Override
    public List<Course> getAll() {
        return transform(courseDao.getAll(), courseMapper::courseToCourse);
    }

    @Override
    public void insert(Course object) {
        courseDao.insert(courseMapper.courseToCourse(object));
    }

    @Override
    public void insert(Collection<Course> objects) {
        courseDao.insert(transform(objects, courseMapper::courseToCourse));
    }

    @Override
    public void update(Course object) {
        courseDao.update(courseMapper.courseToCourse(object));
    }

    @Override
    public void update(Collection<Course> objects) {
        courseDao.update(transform(objects, courseMapper::courseToCourse));
    }

    @Override
    public void delete(Course object) {
        courseDao.delete(courseMapper.courseToCourse(object));
    }

    @Override
    public void deleteById(String s) {
        courseDao.deleteById(s);
    }

    @Override
    public void delete(Collection<Course> objects) {
        courseDao.delete(transform(objects, courseMapper::courseToCourse));
    }
}