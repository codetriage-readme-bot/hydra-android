package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.usecases.EmptyResult;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * Abstract class with common logic for sources that can be enabled or disabled.
 *
 * @author Niko Strijbol
 */
public abstract class OptionalFeedSource implements FeedSource {

    @Override
    public LiveData<Result<List<HomeCard>>> execute(HomeFeedOptions options) {
        if (options.isEnabled(getCardType())) {
            return getActualData();
        } else {
            return new EmptyResult<>();
        }
    }

    /**
     * Get the actual data.
     *
     * @return The data.
     */
    protected abstract LiveData<Result<List<HomeCard>>> getActualData();
}