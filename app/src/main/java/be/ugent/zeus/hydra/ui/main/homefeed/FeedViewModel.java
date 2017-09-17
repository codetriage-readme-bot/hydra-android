package be.ugent.zeus.hydra.ui.main.homefeed;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.usecases.homefeed.GetHomeFeed;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class FeedViewModel extends AndroidViewModel {

    private final GetHomeFeed useCase;

    private LiveData<Result<List<HomeCard>>> data;

    public FeedViewModel(Application application) {
        super(application);
        useCase = HydraApplication.getComponent(application).getHomeFeed();
    }

    public LiveData<Result<List<HomeCard>>> getData() {
        if (data == null) {
            data = useCase.execute(null);
        }
        return data;
    }
}