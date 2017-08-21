package be.ugent.zeus.hydra.data.database.minerva2;

import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.course.DatabaseCourseRepository;
import be.ugent.zeus.hydra.domain.minerva.CourseRepository;

/**
 * @author Niko Strijbol
 */
public final class RepositoryFactory {

    private static DatabaseCourseRepository repository;

    public static synchronized CourseRepository getDatabaseRepository(Context context) {
        if (repository == null) {
            repository = new DatabaseCourseRepository(context);
        }
        return repository;
    }


}
