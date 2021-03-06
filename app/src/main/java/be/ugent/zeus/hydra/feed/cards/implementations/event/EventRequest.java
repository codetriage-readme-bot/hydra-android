package be.ugent.zeus.hydra.feed.cards.implementations.event;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.association.event.EventFilter;
import be.ugent.zeus.hydra.association.event.Event;
import be.ugent.zeus.hydra.feed.cards.Card;
import be.ugent.zeus.hydra.feed.cards.CardRepository;
import be.ugent.zeus.hydra.common.request.Request;
import be.ugent.zeus.hydra.common.request.Requests;
import be.ugent.zeus.hydra.common.request.Result;
import be.ugent.zeus.hydra.feed.HideableHomeFeedRequest;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;
import org.threeten.bp.ZonedDateTime;

import java.util.Arrays;
import java.util.List;

/**
 * Home feed request for association events. We only display events between now and 1 months from now.
 *
 * @author Niko Strijbol
 */
public class EventRequest extends HideableHomeFeedRequest {

    private final Request<List<Event>> request;

    public EventRequest(Context context, CardRepository cardRepository) {
        super(cardRepository);
        this.request = Requests.map(
                Requests.map(Requests.cache(context, new be.ugent.zeus.hydra.association.event.EventRequest()), Arrays::asList),
                new EventFilter(context)
        );
    }

    @Override
    public int getCardType() {
        return Card.Type.ACTIVITY;
    }

    @NonNull
    @Override
    protected Result<Stream<Card>> performRequestCards(@Nullable Bundle args) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime plusOne = now.plusMonths(1);

        return request.performRequest(args).map(events -> StreamSupport.stream(events)
                .filter(c -> c.getStart().isAfter(now) && c.getStart().isBefore(plusOne))
                .map(EventCard::new));
    }
}