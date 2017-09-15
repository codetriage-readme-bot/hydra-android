package be.ugent.zeus.hydra.ui.minerva.overview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetAnnouncementsForCourse;
import be.ugent.zeus.hydra.repository.requests.Result;
import java8.util.Objects;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class AnnouncementViewModel extends AndroidViewModel {

    private String courseId;
    private final GetAnnouncementsForCourse useCase;
    private LiveData<Result<List<Announcement>>> data;

    public AnnouncementViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getUnreadAnnouncementsForCourse();
    }

    public void setCourse(String courseId) {
        this.courseId = courseId;
    }

    public LiveData<Result<List<Announcement>>> getData() {
        Objects.requireNonNull(courseId, "You must set the course before using the view model.");
        if (data == null) {
            data = Transformations.map(useCase.execute(courseId), Result.Builder::fromData);
        }
        return data;
    }
}