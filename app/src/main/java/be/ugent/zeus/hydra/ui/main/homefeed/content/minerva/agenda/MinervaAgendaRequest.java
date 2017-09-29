package be.ugent.zeus.hydra.ui.main.homefeed.content.minerva.agenda;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import be.ugent.zeus.hydra.data.database.minerva.AgendaDao;
import be.ugent.zeus.hydra.data.network.minerva.models.AgendaItem;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.ui.main.homefeed.HomeFeedRequest;
import java8.util.stream.Collectors;
import java8.util.stream.RefStreams;
import java8.util.stream.Stream;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

import java.util.List;
import java.util.Map;

/**
 * Request for Minerva agenda items.
 *
 * @author Niko Strijbol
 */
public class MinervaAgendaRequest implements HomeFeedRequest {

    private final AgendaDao dao;

    public MinervaAgendaRequest(Context context) {
        this.dao = new AgendaDao(context);
    }

    @NonNull
    @Override
    public Result<Stream<HomeCard>> performRequest(Bundle args) {

        ZonedDateTime now = ZonedDateTime.now();
        // Only display things up to 3 weeks from now.
        ZonedDateTime oneMonth = now.plusWeeks(3);
        //Note: in real Java 8 streams, we could concat the operations below.
        //Sort the items per day
        Map<LocalDate, List<AgendaItem>> perDay = dao.getFutureAgenda(now.toInstant(), oneMonth.toInstant())
                        .collect(Collectors.groupingBy(AgendaItem::getLocalStartDate));

        //Convert it to a view
        return Result.Builder.fromData(RefStreams.empty());
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.MINERVA_AGENDA;
    }
}