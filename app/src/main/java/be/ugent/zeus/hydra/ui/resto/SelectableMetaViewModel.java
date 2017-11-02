package be.ugent.zeus.hydra.ui.resto;

import android.app.Application;

import be.ugent.zeus.hydra.data.network.requests.resto.SelectableMetaRequest;
import be.ugent.zeus.hydra.repository.requests.Request;
import be.ugent.zeus.hydra.ui.common.RequestViewModel;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class SelectableMetaViewModel extends RequestViewModel<List<SelectableMetaRequest.RestoChoice>> {

    public SelectableMetaViewModel(Application application) {
        super(application);
    }

    @Override
    protected Request<List<SelectableMetaRequest.RestoChoice>> getRequest() {
        return new SelectableMetaRequest(getApplication());
    }
}