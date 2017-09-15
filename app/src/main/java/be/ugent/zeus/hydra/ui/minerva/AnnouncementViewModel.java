package be.ugent.zeus.hydra.ui.minerva;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetSingleAnnouncement;

/**
 * @author Niko Strijbol
 */
public class AnnouncementViewModel extends AndroidViewModel {

    private LiveData<Announcement> data;
    private int id;
    private final GetSingleAnnouncement useCase;

    public AnnouncementViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getSingleAnnouncement();
    }

    public void setId(int id) {
        this.id = id;
    }

    public LiveData<Announcement> getData() {
        if (data == null) {
            data = useCase.execute(id);
        }
        return data;
    }
}