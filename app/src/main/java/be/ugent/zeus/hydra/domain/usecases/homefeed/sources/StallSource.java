package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;
import android.util.Log;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.homefeed.OptionalFeedSource;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class StallSource extends OptionalFeedSource {

    private final Executor executor;

    @Inject
    public StallSource(@Named(Executor.BACKGROUND) Executor executor) {
        this.executor = executor;
    }

    @Override
    protected LiveData<Result<List<HomeCard>>> getActualData(Args ignored) {
        MutableLiveData<Result<List<HomeCard>>> data = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                Log.w("DEBUG SOURCE", "DOING DEBUG");
                Log.i("DEBUG SOURCE", "Observer: Is this the main thread: " + (Looper.myLooper() == Looper.getMainLooper()));
                Thread.sleep(5000); //Sleep 5 seconds
                Log.w("DEBUG SOURCE", "DEBUG IS DONE");
                data.postValue(Result.Builder.fromData(Collections.emptyList()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return data;
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.DEBUG;
    }
}
