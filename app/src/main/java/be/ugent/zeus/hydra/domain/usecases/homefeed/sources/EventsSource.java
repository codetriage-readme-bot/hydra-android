package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveDataInterface;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.entities.homefeed.cards.EventCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.event.GetEvents;
import be.ugent.zeus.hydra.domain.usecases.homefeed.OptionalFeedSource;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import org.threeten.bp.Duration;
import org.threeten.bp.ZonedDateTime;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class EventsSource extends OptionalFeedSource {

    public static final Duration MAX_EVENT_IN_ADVANCE = Duration.ofDays(30);

    private final GetEvents wrapped;

    @Inject
    public EventsSource(GetEvents wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    protected LiveDataInterface<Result<List<HomeCard>>> getActualData(Args args) {
        return wrapped.execute(null).map(listResult -> listResult.map(events -> {
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime plusOne = now.plus(MAX_EVENT_IN_ADVANCE);
            return StreamSupport.stream(events)
                    .filter(c -> c.getStart().isAfter(now) && c.getStart().isBefore(plusOne))
                    .map(EventCard::new)
                    .collect(Collectors.toList());
        }));
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.ACTIVITY;
    }
}