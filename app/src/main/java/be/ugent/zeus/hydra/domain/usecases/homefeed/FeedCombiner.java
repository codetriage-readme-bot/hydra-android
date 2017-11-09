package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.core.util.Function;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.ui.main.homefeed.FeedException;
import java8.lang.Iterables;

import java.util.*;

/**
 *
 * @author Niko Strijbol
 */

public class FeedCombiner implements Function<Pair<Integer, Result<List<HomeCard>>>, Result<List<HomeCard>>>, java8.util.function.Function<Pair<Integer, Result<List<HomeCard>>>, Result<List<HomeCard>>> {

    private int nrStillWaiting = 0;
    private final List<HomeCard> feedItems = new ArrayList<>();
    private final Set<Integer> errors = new HashSet<>();

    @Override
    public Result<List<HomeCard>> apply(Pair<Integer, Result<List<HomeCard>>> listResult) {

        Log.i("TEMP-FEED-COMBINER", "execute: Is this the main thread: " + (Looper.getMainLooper().getThread() == Thread.currentThread()));
        
        Result.Builder<List<HomeCard>> builder = new Result.Builder<>();

        if (!errors.isEmpty() || listResult.second.hasException()) {
            if (listResult.second.hasException()) {
                errors.add(listResult.first);
            } else {
                errors.remove(listResult.first);
            }
            if (!errors.isEmpty()) {
                builder.withError(new FeedException(errors));
            }
        }

        // Remove existing cards for this type.
        Iterables.removeIf(feedItems, homeCard -> homeCard.getCardType() == listResult.first);

        feedItems.addAll(listResult.second.orElse(Collections.emptyList()));

        Collections.sort(feedItems);

        builder.withData(feedItems);

        Result<List<HomeCard>> result;

        // If this is the last source to complete, set it to final.
        if (nrStillWaiting <= 1) {
            result = builder.build();
        } else {
            result = builder.buildPartial();
        }

        nrStillWaiting--;
        return result;
    }

    public synchronized void reset(int nrStillWaiting) {
        this.nrStillWaiting = nrStillWaiting;
        errors.clear();
    }
}