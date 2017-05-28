package be.ugent.zeus.hydra.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import be.ugent.zeus.hydra.data.network.Request;

/**
 * A live data that will listen to refresh requests. It will reload the data if the refresh broadcast was received.
 * @author Niko Strijbol
 */
public class RefreshingLiveData<M> extends ModelLiveData<M> {

    private final BroadcastReceiver receiver;

    public RefreshingLiveData(Context context, Request<M> model) {
        super(context, model);
        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadData(intent.getExtras());
            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.registerReceiver(receiver, RefreshBroadcast.getBroadcastFilter());
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.unregisterReceiver(receiver);
    }
}