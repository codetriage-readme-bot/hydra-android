package be.ugent.zeus.hydra.ui.minerva;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetSingleCalendarItem;
import be.ugent.zeus.hydra.repository.requests.Result;

/**
 * @author Niko Strijbol
 */
public class AgendaViewModel extends AndroidViewModel {

    private int id;
    private final GetSingleCalendarItem useCase;
    private LiveData<Result<AgendaItem>> data;

    public AgendaViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getSingleCalendarItem();
    }

    public void setId(int id) {
        this.id = id;
    }

    public LiveData<Result<AgendaItem>> getData() {
        if (data == null) {
            data = Transformations.map(useCase.execute(id), agendaItem -> Result.Builder.fromNullable(agendaItem, new NotFoundException("There is no agenda item with id: " + id)));
        }
        return data;
    }
}