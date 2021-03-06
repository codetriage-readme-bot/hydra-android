package be.ugent.zeus.hydra.schamper.list;

import android.app.Application;

import be.ugent.zeus.hydra.common.request.Request;
import be.ugent.zeus.hydra.common.request.Requests;
import be.ugent.zeus.hydra.common.ui.RequestViewModel;
import be.ugent.zeus.hydra.schamper.Article;
import be.ugent.zeus.hydra.schamper.SchamperArticlesRequest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class SchamperViewModel extends RequestViewModel<List<Article>> {

    public SchamperViewModel(Application application) {
        super(application);
    }

    @Override
    protected Request<List<Article>> getRequest() {
        return Requests.map(Requests.cache(getApplication(), new SchamperArticlesRequest()), Arrays::asList);
    }
}