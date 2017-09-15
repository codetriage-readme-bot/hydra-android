package be.ugent.zeus.hydra.ui.minerva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import be.ugent.zeus.hydra.data.database.minerva.AgendaDao;
import be.ugent.zeus.hydra.data.models.minerva.AgendaItem;
import be.ugent.zeus.hydra.repository.data.BaseLiveData;
import be.ugent.zeus.hydra.repository.requests.Result;

/**
 * @author Niko Strijbol
 */
public class AgendaLiveData extends BaseLiveData<Result<AgendaItem>> {

    private final AgendaDao dao;
    private final int id;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadData(Bundle.EMPTY);
        }
    };

    public AgendaLiveData(Context context, int id) {
        this.dao = new AgendaDao(context);
        this.id = id;
        loadData(Bundle.EMPTY);
    }

    @Override
    protected void loadData(Bundle args) {
        new AsyncTask<Void, Void, Result<AgendaItem>>() {

            @Override
            protected Result<AgendaItem> doInBackground(Void... voids) {

                AgendaItem item = dao.getItem(id);

                if (item == null) {
                    return Result.Builder.fromException(new NotFoundException("Agenda item with ID " + id + " was not found."));
                } else {
                    return Result.Builder.fromData(item);
                }
            }

            @Override
            protected void onPostExecute(Result<AgendaItem> agendaItemResult) {
                setValue(agendaItemResult);
            }
        }.execute();
    }
}