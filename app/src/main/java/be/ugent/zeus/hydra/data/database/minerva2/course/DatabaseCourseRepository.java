package be.ugent.zeus.hydra.data.database.minerva2.course;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.data.database.minerva2.NullAwareTransformations;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.CourseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static be.ugent.zeus.hydra.utils.IterableUtils.transform;

/**
 * @author Niko Strijbol
 */
@Singleton
public class DatabaseCourseRepository implements CourseRepository {

    private final CourseDao courseDao;
    private final CourseMapper courseMapper;

    @Inject
    public DatabaseCourseRepository(CourseDao courseDao, CourseMapper courseMapper) {
        this.courseDao = courseDao;
        this.courseMapper = courseMapper;
    }

    @Override
    public LiveData<Course> getOneLive(String id) {
        return NullAwareTransformations.map(courseDao.getOneLive(id), courseMapper::courseToCourse);
    }

    @Override
    public Course getOne(String id) {
        return courseMapper.courseToCourse(courseDao.getOne(id));
    }

    @Override
    public LiveData<List<Course>> getAllLive() {
        return NullAwareTransformations.map(courseDao.getAllLive(), i -> transform(i, courseMapper::courseToCourse));
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
    public void deleteById(Collection<String> id) {
        courseDao.deleteById(new ArrayList<>(id));
    }

    @Override
    public void delete(Collection<Course> objects) {
        courseDao.delete(transform(objects, courseMapper::courseToCourse));
    }

    @Override
    public List<Course> getIn(List<String> ids) {
        return transform(courseDao.getIn(ids), courseMapper::courseToCourse);
    }

    @Override
    public LiveData<List<Course>> getInLive(List<String> ids) {
        return NullAwareTransformations.map(courseDao.getInLive(ids), l -> transform(l, courseMapper::courseToCourse));
    }

    @Override
    public LiveData<List<CourseUnread>> getAllAndUnreadInOrder() {
        return NullAwareTransformations.map(courseDao.getAllAndUnreadInOrder(), l -> transform(l, courseMapper::convert));
    }

    @Override
    public List<String> getIds() {
        return courseDao.getIds();
    }
}