package be.ugent.zeus.hydra.ui.minerva.overview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetCalendarForCourse;
import be.ugent.zeus.hydra.domain.requests.Result;
import java8.util.Objects;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class AgendaViewModel extends AndroidViewModel {

    private String courseId;
    private LiveData<Result<List<AgendaItem>>> data;
    private final GetCalendarForCourse useCase;

    public AgendaViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getCalendarForCourse();
    }

    public void setCourse(String courseId) {
        this.courseId = courseId;
    }

    public LiveData<Result<List<AgendaItem>>> getData() {
        Objects.requireNonNull(courseId, "You must set the course before using the view model.");
        if (data == null) {
            data = Transformations.map(useCase.execute(new GetCalendarForCourse.Params(courseId, true)), Result.Builder::fromData);
        }
        return data;
    }
}