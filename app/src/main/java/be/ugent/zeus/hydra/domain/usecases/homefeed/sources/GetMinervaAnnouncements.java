package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveData;
import android.os.Looper;
import android.util.Log;

import be.ugent.zeus.hydra.domain.LiveDataUtils;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.entities.homefeed.cards.MinervaAnnouncementsCard;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.homefeed.OptionalFeedSource;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AnnouncementRepository;
import be.ugent.zeus.hydra.repository.requests.Result;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * @author Niko Strijbol
 */
public class GetMinervaAnnouncements extends OptionalFeedSource {

    private final AnnouncementRepository repository;
    private final Executor executor;

    @Inject
    public GetMinervaAnnouncements(AnnouncementRepository repository, @Named(Executor.BACKGROUND) Executor executor) {
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    protected LiveData<Result<List<HomeCard>>> getActualData() {
        return LiveDataUtils.mapAsync(executor, repository.getLiveUnreadMostRecentFirst(), announcements -> {
            Log.i("TEMP-FEED-ANNOUNCEMENT", "executOR: Is this the main thread: " + (Looper.myLooper() == Looper.getMainLooper()));
            // Partition it by course.
            Map<Course, List<Announcement>> partitioned = StreamSupport.stream(announcements)
                    .collect(Collectors.groupingBy(Announcement::getCourse));

            List<HomeCard> result = StreamSupport.stream(partitioned.entrySet())
                    .map(courseListEntry -> new MinervaAnnouncementsCard(courseListEntry.getValue(), courseListEntry.getKey()))
                    .collect(Collectors.toList());

            return Result.Builder.fromData(result);
        });
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.MINERVA_ANNOUNCEMENT;
    }
}