package android.arch.lifecycle;

import android.support.annotation.Nullable;

/**
 * @author Niko Strijbol
 */
public class Source<V> implements Observer<V> {
    private final LiveData<V> liveData;
    private final Observer<V> observer;
    private int mVersion = -1;

    public Source(LiveData<V> liveData, Observer<V> observer) {
        this.liveData = liveData;
        this.observer = observer;
    }

    public void plug() {
        this.liveData.observeForever(this);
    }

    public void unplug() {
        this.liveData.removeObserver(this);
    }

    public void onChanged(@Nullable V v) {
        if (this.mVersion != this.liveData.getVersion()) {
            this.mVersion = this.liveData.getVersion();
            this.observer.onChanged(v);
        }
    }

    public Observer<V> getObserver() {
        return observer;
    }
}