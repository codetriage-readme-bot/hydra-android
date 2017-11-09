package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveDataInterface;
import android.arch.lifecycle.MergeLiveData;
import android.arch.lifecycle.RefreshLiveDataInterface;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.util.Log;
import android.util.Pair;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.UseCase;

import javax.inject.Inject;
import java.util.List;

/**
 * Get the home card.
 *
 * @author Niko Strijbol
 */
public class GetHomeFeed extends MergeLiveData<Result<List<HomeCard>>> implements UseCase<Void, RefreshLiveDataInterface<Result<List<HomeCard>>>> {

    private final HomeFeedOptions options;
    private final FeedSourceProvider sourceProvider;

    @Inject
    public GetHomeFeed(HomeFeedOptions options, FeedSourceProvider provider) {
        this.options = options;
        this.sourceProvider = provider;
        setUp();
    }

    @Override
    public RefreshLiveDataInterface<Result<List<HomeCard>>> execute(Void ignored) {
        return this;
    }

    @MainThread
    private void setUp() {

        Log.i("TEMP-FEED", "execute: Is this the main thread: " + (Looper.getMainLooper().getThread() == Thread.currentThread()));

        // Actually combines all feeds into one.
        FeedCombiner feedCombiner = new FeedCombiner();

        FeedSource.Args arguments = new FeedSource.Args(options);

        // Map and add the sources.
        for (FeedSource source: getSources()) {
            LiveDataInterface<Result<List<HomeCard>>> result = source.execute(arguments)
                    .map(listResult -> new Pair<>(source.getCardType(), listResult))
                    .map(feedCombiner);
            this.addSource(result);
        }
    }

    private List<FeedSource> getSources() {
        return sourceProvider.getAll();
    }
}