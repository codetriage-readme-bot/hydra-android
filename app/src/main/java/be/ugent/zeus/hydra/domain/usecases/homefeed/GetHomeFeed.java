package be.ugent.zeus.hydra.domain.usecases.homefeed;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.usecases.BackgroundExecutor;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.repository.requests.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Get the home card.
 *
 * @author Niko Strijbol
 */
public class GetHomeFeed implements UseCase<GetHomeFeed.Params, LiveData<Result<List<HomeCard>>>> {

    private final HomeFeedOptions options;
    private final BackgroundExecutor backgroundExecutor;

    @Inject
    public GetHomeFeed(HomeFeedOptions options, BackgroundExecutor backgroundExecutor) {
        this.options = options;
        this.backgroundExecutor = backgroundExecutor;
    }

    @Override
    public LiveData<Result<List<HomeCard>>> execute(Params arguments) {
        return null;
    }

    public static class Params {

        /**
         * Which card types to load in the feed.
         */
        public Set<Integer> cardTypes;
    }


}