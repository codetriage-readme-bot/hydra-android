package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.repository.requests.RequestException;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public abstract class FeedData extends LiveData<List<HomeCard>> {

    public FeedData() {
        loadData();
    }

    protected abstract List<HomeCard> getDataBackground() throws RequestException;

    private void loadData() {
        new AsyncTask<Void,Void, Result<List<HomeCard>>>() {
            @Override
            protected Result<List<HomeCard>> doInBackground(Void... voids) {
                try {
                    return Result.Builder.fromData(getDataBackground());
                } catch (RequestException e) {
                    return Result.Builder.fromException(e);
                }
            }
        }.execute();
    }
}