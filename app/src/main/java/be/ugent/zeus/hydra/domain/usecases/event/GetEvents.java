package be.ugent.zeus.hydra.domain.usecases.event;

import android.content.SharedPreferences;
import android.os.Bundle;

import be.ugent.zeus.hydra.domain.Preferences;
import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.entities.event.Event;
import be.ugent.zeus.hydra.domain.requests.Request;
import be.ugent.zeus.hydra.domain.requests.Requests;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.utils.PreferenceLiveData;
import be.ugent.zeus.hydra.domain.utils.livedata.LiveDataInterface;
import java8.util.Comparators;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Get events from associations. The events are cached and filtered according to the user's preferences, which contain
 * a list of associations from which they do not want to see events.
 *
 * By default this use case is executed on a background thread. See the constructor for more details.
 *
 * @author Niko Strijbol
 */
public class GetEvents extends PreferenceLiveData<Result<List<Event>>> implements UseCase<Void, LiveDataInterface<Result<List<Event>>>> {

    private final Request<Event[]> request;
    private final SharedPreferences preferences;

    @Inject
    public GetEvents(Cache cache, @Named(Executor.BACKGROUND) Executor executor, Request.Cacheable<Event[]> request, SharedPreferences preferences) {
        super(executor, preferences, false);
        this.preferences = preferences;
        this.request = Requests.cache(cache, request);
    }

    @Override
    public LiveDataInterface<Result<List<Event>>> execute(Void arguments) {
        requestLoad();
        return this;
    }

    @Override
    protected Collection<String> getPreferenceKeys() {
        return Collections.singleton(Preferences.DISABLED_ASSOCIATIONS);
    }

    @Override
    protected Result<List<Event>> doCalculations(Executor.Companion companion, Bundle args) {
        return request.performRequest(args)
                .map(Arrays::asList)
                .map(this::filterAndSort);
    }

    private List<Event> filterAndSort(List<Event> rawEvents) {
        Set<String> disabled = preferences.getStringSet(Preferences.DISABLED_ASSOCIATIONS, Collections.emptySet());
        return StreamSupport.stream(rawEvents)
                .filter(e -> !disabled.contains(e.getAssociation().getInternalName()))
                .sorted(Comparators.comparing(Event::getStart))
                .collect(Collectors.toList());
    }
}