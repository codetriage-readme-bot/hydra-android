package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public interface FeedSource extends UseCase<HomeFeedOptions, LiveData<Result<List<HomeCard>>>> {

    /**
     * The type of card that will be added/removed by this operation.
     *
     * @return The type.
     */
    @HomeCard.CardType
    int getCardType();
}