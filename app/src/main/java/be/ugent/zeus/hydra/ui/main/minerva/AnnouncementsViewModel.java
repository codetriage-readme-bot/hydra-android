package be.ugent.zeus.hydra.ui.main.minerva;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.data.database.minerva2.RepositoryFactory;
import be.ugent.zeus.hydra.domain.minerva.Announcement;
import be.ugent.zeus.hydra.domain.minerva.AnnouncementRepository;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class AnnouncementsViewModel extends AndroidViewModel {

    private LiveData<Result<List<Announcement>>> data;

    public AnnouncementsViewModel(Application application) {
        super(application);
    }

    protected AnnouncementsLiveData constructDataInstance() {
        return new AnnouncementsLiveData(getApplication());
    }

    public LiveData<Result<List<Announcement>>> getData() {
        if (data == null) {
            AnnouncementRepository repository = RepositoryFactory.getAnnouncementDatabaseRepository(getApplication());
            data = Transformations.map(repository.getLiveUnreadMostRecentFirst(), Result.Builder::fromData);
        }

        return data;
    }
}