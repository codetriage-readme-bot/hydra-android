package be.ugent.zeus.hydra.ui.main.homefeed;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Bundle;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.usecases.homefeed.FeedLiveData;
import be.ugent.zeus.hydra.domain.usecases.homefeed.GetHomeFeed;

/**
 * @author Niko Strijbol
 */
public class FeedViewModel extends AndroidViewModel {

    private final GetHomeFeed useCase;

    private FeedLiveData data;

    public FeedViewModel(Application application) {
        super(application);
        useCase = HydraApplication.getComponent(application).getHomeFeed();
    }

    public FeedLiveData getData() {
        if (data == null) {
            data = useCase.execute(null);
        }
        return data;
    }

    public void requestRefresh() {
        if (data != null) {
            data.flagForRefresh(Bundle.EMPTY);
        }
    }
}