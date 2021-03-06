package be.ugent.zeus.hydra.sko.lineup;

import android.app.Application;

import be.ugent.zeus.hydra.common.request.Request;
import be.ugent.zeus.hydra.common.request.Requests;
import be.ugent.zeus.hydra.common.ui.RequestViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * This class cannot be package private due to technical limitations.
 *
 * @author Niko Strijbol
 */
public class LineupViewModel extends RequestViewModel<List<Artist>> {

    public LineupViewModel(Application application) {
        super(application);
    }

    @Override
    protected Request<List<Artist>> getRequest() {
        return Requests.map(Requests.cache(getApplication(), new LineupRequest()), Arrays::asList);
    }
}