package be.ugent.zeus.hydra.ui.main.minerva;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetUnreadAnnouncements;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class AnnouncementsViewModel extends AndroidViewModel {

    private final GetUnreadAnnouncements useCase;
    private LiveData<Result<List<Announcement>>> data;

    public AnnouncementsViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getUnreadAnnouncement();
    }

    public LiveData<Result<List<Announcement>>> getData() {
        if (data == null) {
            data = Transformations.map(useCase.execute(null), Result.Builder::fromData);
        }

        return data;
    }
}