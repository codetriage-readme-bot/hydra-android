package be.ugent.zeus.hydra.ui.main.homefeed.content.minerva.announcement;

import android.os.Bundle;
import android.support.annotation.NonNull;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.repository.requests.Result;
import be.ugent.zeus.hydra.ui.main.homefeed.HomeFeedRequest;
import java8.util.stream.RefStreams;
import java8.util.stream.Stream;

/**
 * @author Niko Strijbol
 */
public class MinervaAnnouncementRequest implements HomeFeedRequest {

    public MinervaAnnouncementRequest() {
    }

    @NonNull
    @Override
    public Result<Stream<HomeCard>> performRequest(Bundle args) {
//        return Result.Builder.fromData(StreamSupport.stream(dao.getUnread().entrySet())
//                .map(s -> new MinervaAnnouncementsCard(s.getValue(), s.getKey())));
        return Result.Builder.fromData(RefStreams.empty());
    }

    @Override
    public int getCardType() {
        return HomeCard.CardType.MINERVA_ANNOUNCEMENT;
    }
}