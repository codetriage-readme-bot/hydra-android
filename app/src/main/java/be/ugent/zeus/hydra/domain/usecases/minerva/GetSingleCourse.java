package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.CourseRepository;

import javax.inject.Inject;

/**
 * @author Niko Strijbol
 */
public class GetSingleCourse implements UseCase<String,LiveData<Course>> {

    private final CourseRepository courseRepository;

    @Inject
    public GetSingleCourse(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public LiveData<Course> execute(String id) {
        return courseRepository.getOneLive(id);
    }
}