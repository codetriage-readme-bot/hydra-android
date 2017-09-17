package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.util.SparseArray;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class FeedLiveData extends MediatorLiveData<Result<List<HomeCard>>> {

    private SparseArray<LiveData<?>> sources = new SparseArray<>();

    @Override
    public <S> void addSource(LiveData<S> source, Observer<S> onChanged) {
        super.addSource(source, onChanged);
    }

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
}
