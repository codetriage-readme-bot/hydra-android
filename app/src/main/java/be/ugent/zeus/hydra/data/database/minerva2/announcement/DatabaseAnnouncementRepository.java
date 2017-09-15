package be.ugent.zeus.hydra.data.database.minerva2.announcement;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.MinervaDatabase;
import be.ugent.zeus.hydra.data.database.minerva2.NullAwareTransformations;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseDao;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseMapper;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.minerva.AnnouncementRepository;
import org.mapstruct.factory.Mappers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;

import static be.ugent.zeus.hydra.utils.IterableUtils.transform;

/**
 * @author Niko Strijbol
 */
@Singleton
public class DatabaseAnnouncementRepository implements AnnouncementRepository {

    private final AnnouncementDao announcementDao;
    private final CourseDao courseDao;
    private final CourseMapper courseMapper;
    private final AnnouncementMapper announcementMapper;

    @Inject
    public DatabaseAnnouncementRepository(AnnouncementDao announcementDao, CourseDao courseDao, CourseMapper courseMapper, AnnouncementMapper announcementMapper) {
        this.announcementDao = announcementDao;
        this.courseMapper = courseMapper;
        this.announcementMapper = announcementMapper;
        this.courseDao = courseDao;
    }

    public DatabaseAnnouncementRepository(Context context) {
        this.announcementDao = MinervaDatabase.getInstance(context).getAnnouncementDao();
        this.courseMapper = Mappers.getMapper(CourseMapper.class);
        this.announcementMapper = Mappers.getMapper(AnnouncementMapper.class);
        this.courseDao = MinervaDatabase.getInstance(context).getCourseDao();
    }

    @Override
    public LiveData<Announcement> getOneLive(Integer integer) {
        return NullAwareTransformations.map(announcementDao.getOneLive(integer), result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course)));
    }

    @Override
    public Announcement getOne(Integer integer) {
        AnnouncementDao.Result result = announcementDao.getOne(integer);
        return announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course));
    }

    @Override
    public LiveData<List<Announcement>> getAllLive() {
        return NullAwareTransformations.map(announcementDao.getAllLive(), results -> transform(results, result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course))));
    }

    @Override
    public List<Announcement> getAll() {
        return transform(announcementDao.getAll(), result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course)));
    }

    @Override
    public void insert(Announcement object) {
        announcementDao.insert(announcementMapper.convert(object));
    }

    @Override
    public void insert(Collection<Announcement> objects) {
        announcementDao.insert(transform(objects, announcementMapper::convert));
    }

    @Override
    public void update(Announcement object) {
        announcementDao.update(announcementMapper.convert(object));
    }

    @Override
    public void update(Collection<Announcement> objects) {
        announcementDao.update(transform(objects, announcementMapper::convert));
    }

    @Override
    public void delete(Announcement object) {
        announcementDao.delete(announcementMapper.convert(object));
    }

    @Override
    public void deleteById(Integer integer) {
        announcementDao.delete(integer);
    }

    @Override
    public void delete(Collection<Announcement> objects) {
        announcementDao.delete(transform(objects, announcementMapper::convert));
    }

    @Override
    public LiveData<List<Announcement>> getLiveUnreadMostRecentFirst() {
        return NullAwareTransformations.map(announcementDao.getLiveUnreadMostRecentFirst(), results -> transform(results, result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course))));
    }

    @Override
    public LiveData<List<Announcement>> getLiveMostRecentFirst(String courseId) {
        return NullAwareTransformations.map(announcementDao.getLiveMostRecentFirst(courseId), results -> transform(results, result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course))));
    }

    @Override
    public List<Announcement> getUnreadMostRecentFirst() {
        return transform(announcementDao.getUnreadMostRecentFirst(), result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course)));
    }
}