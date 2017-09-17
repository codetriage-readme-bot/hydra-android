package be.ugent.zeus.hydra.domain.usecases.homefeed.sources;

import android.arch.lifecycle.LiveData;
import android.os.Looper;
import android.util.Log;

import be.ugent.zeus.hydra.domain.LiveDataUtils;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.entities.homefeed.cards.MinervaAgendaCard;
import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.homefeed.OptionalFeedSource;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AgendaItemRepository;
import be.ugent.zeus.hydra.repository.requests.Result;
import be.ugent.zeus.hydra.utils.DateUtils;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * @author Niko Strijbol
 */
public class MinervaAgendaSource extends OptionalFeedSource {

    private final AgendaItemRepository repository;
    private final Executor executor;

    @Inject
    public MinervaAgendaSource(AgendaItemRepository repository, @Named(Executor.BACKGROUND) Executor executor) {
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    protected LiveData<Result<List<HomeCard>>> getActualData() {

        ZonedDateTime lower = ZonedDateTime.now();
        ZonedDateTime upper = lower.plusWeeks(3); // Only display things up to 3 weeks from now.

        return LiveDataUtils.mapAsync(executor, repository.getBetween(lower, upper), agendaItems -> {
            Log.i("TEMP-FEED-CALENDAR", "executOR: Is this the main thread: " + (Looper.myLooper() == Looper.getMainLooper()));
            // Group per day
            Map<LocalDate, List<AgendaItem>> perDay = StreamSupport.stream(agendaItems)
                    .collect(Collectors.groupingBy(a -> DateUtils.toLocalDateTime(a.getStartDate()).toLocalDate()));

            List<HomeCard> result = StreamSupport.stream(perDay.entrySet())
                    .map(e -> new MinervaAgendaCard(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

            return Result.Builder.fromData(result);
        });
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.MINERVA_AGENDA;
    }
}
