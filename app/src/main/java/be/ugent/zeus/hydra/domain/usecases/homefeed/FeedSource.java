package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public interface FeedSource extends UseCase<HomeFeedOptions, LiveData<Result<List<HomeCard>>>> {

    /**
     * {@inheritDoc}
     *
     * Note: this method will be run on the main thread, so long-running tasks should move to the background using
     * {@link be.ugent.zeus.hydra.domain.usecases.Executor} or similar.
     */
    @Override
    LiveData<Result<List<HomeCard>>> execute(HomeFeedOptions arguments);

    /**
     * The type of card that will be added/removed by this operation.
     *
     * @return The type.
     */
    @HomeCard.CardType
    int getCardType();
}