package be.ugent.zeus.hydra.ui.main.schamper;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.schamper.GetSchamperArticles;
import be.ugent.zeus.hydra.domain.utils.RefreshLiveData;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class SchamperViewModel extends AndroidViewModel implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "RefreshViewModel";

    private final GetSchamperArticles useCase;

    private RefreshLiveData<Result<List<SchamperArticle>>> data;
    private LiveData<Boolean> refreshing;

    public SchamperViewModel(Application application) {
        super(application);
        useCase = HydraApplication.getComponent(application).getSchamperArticles();
    }

    public RefreshLiveData<Result<List<SchamperArticle>>> getData() {
        if (data == null) {
            data = useCase.execute(null);
        }
        return data;
    }

    @Override
    public void onRefresh() {
        getData().requestRefresh();
    }

    /**
     * Construct the refresh live data.
     *
     * @return The refresh live data.
     */
    private LiveData<Boolean> buildRefreshLiveData() {
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();
        result.addSource(getData(), data -> result.setValue(data != null && !data.isDone()));
        getData().registerRefreshListener(() -> result.postValue(true));
        return result;
    }

    /**
     * @return The refreshing status.
     */
    public LiveData<Boolean> getRefreshing() {
        if (refreshing == null) {
            refreshing = buildRefreshLiveData();
        }

        return refreshing;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "Destroyed the view model.");
        refreshing = null;
        data = null;
    }
}