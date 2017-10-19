package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveDataInterface;
import android.arch.lifecycle.SingleLiveData;
import android.os.Bundle;
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
    protected LiveDataInterface<Result<List<HomeCard>>> getActualData(Args ignored) {
        return new SingleLiveData<Result<List<HomeCard>>>(executor) {
            @Override
            protected Result<List<HomeCard>> doCalculations(Executor.Companion companion, Bundle args) {
                try {
                    Log.w("DEBUG SOURCE", "DOING DEBUG");
                    Log.i("DEBUG SOURCE", "Observer: Is this the main thread: " + (Looper.myLooper() == Looper.getMainLooper()));
                    Thread.sleep(5000); //Sleep 5 seconds
                    Log.w("DEBUG SOURCE", "DEBUG IS DONE");
                    return Result.Builder.fromData(Collections.emptyList());
                } catch (InterruptedException e) {
                    return Result.Builder.fromData(Collections.emptyList());
                }
            }
        };
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.DEBUG;
    }
}
