package be.ugent.zeus.hydra.data.database.minerva2;

import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.announcement.DatabaseAnnouncementRepository;
import be.ugent.zeus.hydra.data.database.minerva2.course.DatabaseCourseRepository;
import be.ugent.zeus.hydra.domain.minerva.AnnouncementRepository;
import be.ugent.zeus.hydra.domain.minerva.CourseRepository;

/**
 * @author Niko Strijbol
 */
public final class RepositoryFactory {

    private static DatabaseCourseRepository courseRepository;
    private static DatabaseAnnouncementRepository announcementRepository;

    public static synchronized CourseRepository getCourseDatabaseRepository(Context context) {
        if (courseRepository == null) {
            courseRepository = new DatabaseCourseRepository(context);
        }
        return courseRepository;
    }

    public static synchronized AnnouncementRepository getAnnouncementDatabaseRepository(Context context) {
        if (announcementRepository == null) {
            announcementRepository = new DatabaseAnnouncementRepository(context);
        }
        return announcementRepository;
    }
}