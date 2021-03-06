package be.ugent.zeus.hydra.sko.studentvillage;

import android.app.Application;

import be.ugent.zeus.hydra.common.request.Request;
import be.ugent.zeus.hydra.common.request.Requests;
import be.ugent.zeus.hydra.common.ui.RequestViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class ExhibitorViewModel extends RequestViewModel<List<Exhibitor>> {

    public ExhibitorViewModel(Application application) {
        super(application);
    }

    @Override
    protected Request<List<Exhibitor>> getRequest() {
        return Requests.map(Requests.cache(getApplication(), new ExhibitorRequest()), Arrays::asList);
    }
}
