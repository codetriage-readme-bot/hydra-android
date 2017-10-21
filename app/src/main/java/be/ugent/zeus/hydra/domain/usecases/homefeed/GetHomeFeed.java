package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveDataInterface;
import android.arch.lifecycle.MergeLiveData;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.util.Log;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.ui.main.homefeed.FeedException;
import java8.util.function.BiFunction;
import java8.util.stream.Collectors;
import java8.util.stream.RefStreams;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Get the home card.
 *
 * @author Niko Strijbol
 */
public class GetHomeFeed extends MergeLiveData<Result<List<HomeCard>>> implements UseCase<Void, LiveDataInterface<Result<List<HomeCard>>>>, FeedLiveData.OnRefreshListener {

    private final HomeFeedOptions options;
    private final FeedSourceProvider sourceProvider;

    @Inject
    public GetHomeFeed(HomeFeedOptions options, @Named(Executor.BACKGROUND) Executor executor, FeedSourceProvider provider) {
        super(executor);
        this.options = options;
        this.sourceProvider = provider;
        setUp();
    }

    @Override
    public LiveDataInterface<Result<List<HomeCard>>> execute(Void ignored) {
        //requestRefresh(Bundle.EMPTY);
        return this;
    }

    @MainThread
    private void setUp() {
        Result<List<HomeCard>> begin = new Result.Builder<List<HomeCard>>()
                .withData(Collections.emptyList())
                .buildPartial();
        setValue(begin);

        Log.i("TEMP-FEED", "execute: Is this the main thread: " + (Looper.getMainLooper().getThread() == Thread.currentThread()));

        CountDownLatch latch = new CountDownLatch(sourceProvider.getCount());

        FeedSource.Args arguments = new FeedSource.Args(options);

        Log.i("TEMP-FEED-EXECUTE", "execute2: Is this the main thread: " + (Looper.getMainLooper().getThread() == Thread.currentThread()));
        for (FeedSource source : getSources()) {
            addSource(source.execute(arguments), new FeedObserver(source.getCardType(), latch));
        }
    }

    private List<FeedSource> getSources() {
        return sourceProvider.getAll();
    }

    private static class FeedObserver implements BiFunction<Result<List<HomeCard>>, Result<List<HomeCard>>, Result<List<HomeCard>>> {

        @HomeCard.CardType
        private final int cardType;
        private final CountDownLatch latch;

        private FeedObserver(@HomeCard.CardType int cardType, CountDownLatch latch) {
            this.cardType = cardType;
            this.latch = latch;
        }

        @Override
        public Result<List<HomeCard>> apply(Result<List<HomeCard>> data, Result<List<HomeCard>> listResult) {

            Log.i("TEMP-FEED", "Observer: Is this the main thread: " + (Looper.myLooper() == Looper.getMainLooper()));
            Log.i("TEMP-FEED", "Observer thread is " + Thread.currentThread().getName());
            Log.i("TEMP-FEED", "Observer card type is " + cardType);

            // Only one thread can access the feed
            //synchronized (lock) {
            // Get the existing data
            // This should not be null.
            assert data != null;
            assert listResult != null;

            Result.Builder<List<HomeCard>> builder = new Result.Builder<>();

            if (data.hasException() || listResult.hasException()) {
                Set<Integer> errors;
                if (data.hasException()) {
                    errors = ((FeedException) data.getError()).getFailedTypes();
                } else {
                    errors = new HashSet<>();
                }

                if (listResult.hasException()) {
                    errors.add(cardType);
                }
                builder.withError(new FeedException(errors));
            }

            // There should always be data in the existing result, even if the data is an empty list.
            List<HomeCard> existingData = data.orElse(new ArrayList<>());
            // Remove existing cards for this type.
            Stream<HomeCard> temp = StreamSupport.stream(existingData)
                    .filter(c -> c.getCardType() != cardType);

            Stream<HomeCard> newData = StreamSupport.stream(listResult.orElse(Collections.emptyList()));

            builder.withData(RefStreams.concat(temp, newData)
                    .sorted()
                    .collect(Collectors.toList()));

            Result<List<HomeCard>> result;

            // If this is the last source to complete, set it to final.
            if (latch.getCount() == 1) {
                result = builder.build();
            } else {
                result = builder.buildPartial();
            }

            latch.countDown();
            return result;
            //}
        }
    }
}