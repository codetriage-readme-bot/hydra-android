package be.ugent.zeus.hydra.ui.main.events;

import android.app.Application;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.event.GetEvents;
import be.ugent.zeus.hydra.domain.utils.RefreshLiveData;
import be.ugent.zeus.hydra.ui.common.BetterRefreshViewModel;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class EventViewModel extends BetterRefreshViewModel<List<EventItem>> {

    private final GetEvents useCase;

    public EventViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getEvents();
    }

    @Override
    protected RefreshLiveData<Result<List<EventItem>>> executeUseCase() {
        return useCase.execute(null).map(listResult -> listResult.map(new EventItem.Converter()));
    }
}