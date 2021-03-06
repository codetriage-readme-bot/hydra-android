package be.ugent.zeus.hydra.common.arch.data;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import be.ugent.zeus.hydra.common.request.Request;
import be.ugent.zeus.hydra.common.request.Result;

/**
 * Live data for a {@link Request}.
 *
 * @author Niko Strijbol
 */
public class RequestLiveData<M> extends BaseLiveData<Result<M>> {

    private final Request<M> request;
    private final Context applicationContext;

    public RequestLiveData(Context context, Request<M> request) {
        this.applicationContext = context.getApplicationContext();
        this.request = request;
        loadData(Bundle.EMPTY);
    }

    /**
     * Load the actual data.
     *
     * @param bundle The arguments for the request.
     */
    @Override
    protected void loadData(@Nullable Bundle bundle) {
        new AsyncTask<Void, Void, Result<M>>() {

            @Override
            protected Result<M> doInBackground(Void... voids) {
                return request.performRequest(bundle);
            }

            @Override
            protected void onPostExecute(Result<M> m) {
                super.onPostExecute(m);
                setValue(m);
            }
        }
        .execute();
    }

    protected Context getContext() {
        return applicationContext;
    }
}