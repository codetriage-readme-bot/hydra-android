package be.ugent.zeus.hydra.data.database.minerva2.announcement;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.MinervaDatabase;
import be.ugent.zeus.hydra.data.database.minerva2.course.CourseMapper;
import be.ugent.zeus.hydra.domain.minerva.Announcement;
import be.ugent.zeus.hydra.domain.minerva.AnnouncementRepository;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

import static be.ugent.zeus.hydra.utils.IterableUtils.transform;

/**
 * @author Niko Strijbol
 */
public class DatabaseAnnouncementRepository implements AnnouncementRepository {

    private final AnnouncementDao announcementDao;
    private final CourseMapper courseMapper;
    private final AnnouncementMapper announcementMapper;

    public DatabaseAnnouncementRepository(AnnouncementDao announcementDao, CourseMapper courseMapper, AnnouncementMapper announcementMapper) {
        this.announcementDao = announcementDao;
        this.courseMapper = courseMapper;
        this.announcementMapper = announcementMapper;
    }

    public DatabaseAnnouncementRepository(Context context) {
        this.announcementDao = MinervaDatabase.getInstance(context).getAnnouncementDao();
        this.courseMapper = Mappers.getMapper(CourseMapper.class);
        this.announcementMapper = Mappers.getMapper(AnnouncementMapper.class);
    }

    @Override
    public LiveData<Announcement> getOneLive(Integer integer) {
        return Transformations.map(announcementDao.getOneLive(integer), result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course)));
    }

    @Override
    public Announcement getOne(Integer integer) {
        AnnouncementDao.Result result = announcementDao.getOne(integer);
        return announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course));
    }

    @Override
    public LiveData<List<Announcement>> getAllLive() {
        return Transformations.map(announcementDao.getAllLive(), results -> transform(results, result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course))));
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
        return Transformations.map(announcementDao.getLiveUnreadMostRecentFirst(), results -> transform(results, result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course))));
    }

    @Override
    public List<Announcement> getUnreadMostRecentFirst() {
        return transform(announcementDao.getUnreadMostRecentFirst(), result -> announcementMapper.convert(result.announcement, courseMapper.courseToCourse(result.course)));
    }
}