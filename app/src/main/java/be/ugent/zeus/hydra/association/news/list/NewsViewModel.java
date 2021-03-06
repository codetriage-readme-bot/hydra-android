package be.ugent.zeus.hydra.association.news.list;

import android.app.Application;

import be.ugent.zeus.hydra.association.news.UgentNewsItem;
import be.ugent.zeus.hydra.association.news.UgentNewsRequest;
import be.ugent.zeus.hydra.common.request.Request;
import be.ugent.zeus.hydra.common.request.Requests;
import be.ugent.zeus.hydra.common.ui.RequestViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class NewsViewModel extends RequestViewModel<List<UgentNewsItem>> {

    public NewsViewModel(Application application) {
        super(application);
    }

    @Override
    protected Request<List<UgentNewsItem>> getRequest() {
        return Requests.map(Requests.cache(getApplication(), new UgentNewsRequest()), Arrays::asList);
    }
}