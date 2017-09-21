package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.utils.EmptyResult;

import java.util.List;

/**
 * Abstract class with common logic for sources that can be enabled or disabled.
 *
 * @author Niko Strijbol
 */
public abstract class OptionalFeedSource implements FeedSource {

    @Override
    public LiveData<Result<List<HomeCard>>> execute(Args arguments) {
        if (isEnabled(arguments)) {
            return getActualData(arguments);
        } else {
            return EmptyResult.emptyResultList();
        }
    }

    /**
     * Get the actual data.
     *
     * @return The data.
     */
    protected abstract LiveData<Result<List<HomeCard>>> getActualData(Args args);

    /**
     * Check if this card type is enabled or not.
     *
     * @param args The card type.
     *
     * @return True if the source is active, false otherwise.
     */
    protected boolean isEnabled(Args args) {
        return args.options.isEnabled(getCardType());
    }
}