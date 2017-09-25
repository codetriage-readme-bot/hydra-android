package be.ugent.zeus.hydra.domain.usecases.minerva.sync;

import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.requests.Request;
import be.ugent.zeus.hydra.domain.requests.RequestException;
import be.ugent.zeus.hydra.domain.usecases.ExceptionUseCase;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.CourseRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class SyncCoursesUseCase implements ExceptionUseCase<Void, Void, RequestException> {

    private final CourseRepository repository;
    private final Request<List<Course>> courseRequest;

    @Inject
    public SyncCoursesUseCase(CourseRepository repository, Request<List<Course>> courseRequest) {
        this.repository = repository;
        this.courseRequest = courseRequest;
    }

    @Override
    public Void execute(Void arguments) throws RequestException {
        // Get the courses from the API.
        List<Course> remoteCourses = courseRequest.performRequest(null).getDataOrThrow();
        // Get existing courses.
        Collection<String> existingIds = repository.getIds();
        // Apply the synchronisation.
        Synchronisation<Course, String> synchronisation = new Synchronisation<>(
                existingIds,
                remoteCourses,
                Course::getId
        );

        // Execute diff
        Synchronisation.Diff<Course, String> diff = synchronisation.diff();
        diff.apply(repository);

        return null;
    }
}