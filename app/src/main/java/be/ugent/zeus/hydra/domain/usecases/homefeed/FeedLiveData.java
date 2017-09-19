package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.util.SparseArray;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.requests.Requests;
import be.ugent.zeus.hydra.domain.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class FeedLiveData extends MediatorLiveData<Result<List<HomeCard>>> {

    private final SparseArray<LiveData<?>> sources = new SparseArray<>();
    private final OnRefreshListener onRefreshListener;

    private Bundle queuedRefresh;

    /**
     * Flag this data for a refresh. If there are active observers, the data is reloaded immediately. If there
     * are no active observers, the data will be reloaded when the next active observer registers.
     *
     * If there are no active observers, the {@code args} are saved and will be used when reloading the data at a later
     * point. This method will discard any args from previous calls to this method.
     */
    public void flagForRefresh(Bundle args) {

        Bundle bundle = new Bundle(args);
        bundle.putBoolean(Requests.IGNORE_CACHE, true);

        if (hasActiveObservers()) {
            onRefreshListener.requestRefresh(bundle);
        } else {
            this.queuedRefresh = bundle;
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (queuedRefresh != null) {
            onRefreshListener.requestRefresh(queuedRefresh);
            queuedRefresh = null;
        }
    }

    public FeedLiveData(OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    @Override
    public <S> void addSource(LiveData<S> source, Observer<S> onChanged) {
        super.addSource(source, onChanged);
    }

    @MainThread
    public synchronized <S> void addSource(@HomeCard.CardType int cardType, LiveData<S> source, Observer<S> onChanged) {
        // Remove existing.
        LiveData<?> existing = sources.get(cardType);
        if (existing != null) {
            removeSource(existing);
        }
        // Add new one. This will replace the previous one.
        sources.put(cardType, source);
        addSource(source, onChanged);
    }

    @FunctionalInterface
    public interface OnRefreshListener {
        void requestRefresh(Bundle args);
    }
}
