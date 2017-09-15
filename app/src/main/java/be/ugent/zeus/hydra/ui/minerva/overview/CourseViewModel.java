package be.ugent.zeus.hydra.ui.minerva.overview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetSingleCourse;
import java8.util.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * A view model to get a single course.
 *
 * @author Niko Strijbol
 */
public class CourseViewModel extends AndroidViewModel {

    private final GetSingleCourse useCase;
    private Map<String, LiveData<Course>> data = new HashMap<>();

    public CourseViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getSingleCourse();
    }

    public synchronized LiveData<Course> getData(String courseId) {
        return Maps.computeIfAbsent(data, courseId, useCase::execute);
    }
}