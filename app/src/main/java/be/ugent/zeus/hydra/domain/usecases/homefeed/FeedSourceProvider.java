package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;
import android.os.Looper;
import android.util.Log;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.usecases.EmptyResult;
import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.GetMinervaAgenda;
import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.GetMinervaAnnouncements;
import be.ugent.zeus.hydra.repository.requests.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@Singleton
public class FeedSourceProvider {

    private final GetMinervaAnnouncements minervaAnnouncements;
    private final GetMinervaAgenda minervaAgenda;

    @Inject
    public FeedSourceProvider(GetMinervaAnnouncements minervaAnnouncements, GetMinervaAgenda agenda) {
        this.minervaAnnouncements = minervaAnnouncements;
        this.minervaAgenda = agenda;
    }

    public int getCount() {
        return 3;
    }

    private FeedSource waitingSource() {
        return new FeedSource() {
            @Override
            public int getCardType() {
                return HomeCard.CardType.DEBUG;
            }

            @Override
            public LiveData<Result<List<HomeCard>>> execute(HomeFeedOptions arguments) {
                Log.w("DEBUG SOURCE", "DOING DEBUG");
                Log.i("DEBUG SOURCE", "Observer: Is this the main thread: " + (Looper.myLooper() == Looper.getMainLooper()));
                try {
                    Thread.sleep(10000);
                    Log.w("DEBUG SOURCE", "DEBUG IS DONE");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new EmptyResult<>();
            }
        };
    }

    public List<FeedSource> getAll() {
        return Arrays.asList(waitingSource(), minervaAnnouncements, minervaAgenda);
    }
}