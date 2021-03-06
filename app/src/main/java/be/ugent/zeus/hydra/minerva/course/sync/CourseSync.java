package be.ugent.zeus.hydra.minerva.course.sync;

import android.accounts.Account;
import android.content.Context;
import android.support.annotation.WorkerThread;

import be.ugent.zeus.hydra.common.request.RequestException;
import be.ugent.zeus.hydra.common.sync.Synchronisation;
import be.ugent.zeus.hydra.minerva.course.Course;
import be.ugent.zeus.hydra.minerva.course.CourseRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static be.ugent.zeus.hydra.utils.IterableUtils.transform;

/**
 * Syncs the courses for Minerva.
 *
 * @author Niko Strijbol
 */
public class CourseSync {

    private final CourseRepository courseDao;
    private final Context context;

    public CourseSync(CourseRepository courseDao, Context context) {
        this.courseDao = courseDao;
        this.context = context;
    }

    @WorkerThread
    public void synchronise(Account account) throws RequestException {

        // Get the courses from the server.
        CoursesMinervaRequest minervaRequest = new CoursesMinervaRequest(context, account);
        List<Course> serverCourses = minervaRequest.performRequest(null)
                .map(courses -> transform(courses.courses, ApiCourseMapper.INSTANCE::courseToCourse))
                .getOrThrow();

        // Get all courses in the database.
        Collection<String> existingIds = courseDao.getIds();

        // We want to keep local data.
        // TODO: perhaps do the reverse, only save the API fields.
        Map<String, CourseRepository.LocalData> orders = courseDao.getIdToLocalData();

        for (Course newCourse: serverCourses) {
            CourseRepository.LocalData localData = orders.get(newCourse.getId());
            if (localData == null) {
                continue;
            }
            newCourse.setOrder(localData.order);
            newCourse.setDisabledModules(localData.disabledModules);
            newCourse.setIgnoreAnnouncements(localData.ignoreAnnouncements);
            newCourse.setIgnoreCalendar(localData.ignoreCalendar);
        }

        // Perform the actual synchronisation.
        Synchronisation<Course, String> synchronisation = new Synchronisation<>(
                existingIds,
                serverCourses,
                Course::getId
        );
        // Execute diff
        synchronisation.diff().apply(courseDao);
    }
}