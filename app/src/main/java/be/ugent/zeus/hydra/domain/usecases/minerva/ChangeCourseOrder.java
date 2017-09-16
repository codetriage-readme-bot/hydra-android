package be.ugent.zeus.hydra.domain.usecases.minerva;

import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.CourseRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Change the order of the list of courses. The new order will be the order in which the courses are in the arguments.
 *
 * This use case is executed synchronously.
 *
 * @author Niko Strijbol
 */
public class ChangeCourseOrder implements UseCase<List<Course>, Void> {

    private final CourseRepository repository;

    @Inject
    public ChangeCourseOrder(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(List<Course> arguments) {

        // Update the actual order in the objects.
        for (int i = 0; i < arguments.size(); i++) {
            arguments.get(i).setOrder(i);
        }

        // Save the changed order.
        repository.update(arguments);

        return null;
    }
}
