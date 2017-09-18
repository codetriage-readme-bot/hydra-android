package be.ugent.zeus.hydra.ui.minerva;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetSingleAnnouncement;
import be.ugent.zeus.hydra.domain.requests.Result;

/**
 * View model for a single announcement.
 *
 * @author Niko Strijbol
 */
public class AnnouncementViewModel extends AndroidViewModel {

    private final GetSingleAnnouncement useCase;
    private LiveData<Result<Announcement>> data;
    private int id;

    public AnnouncementViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getSingleAnnouncement();
    }

    public void setId(int id) {
        this.id = id;
    }

    public LiveData<Result<Announcement>> getData() {
        if (data == null) {
            data = Transformations.map(useCase.execute(id), announcement -> Result.Builder.fromNullable(announcement, new NotFoundException("There is no announcement with id: " + id)));
        }
        return data;
    }
}