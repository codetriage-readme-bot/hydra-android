package be.ugent.zeus.hydra.domain.usecases.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import be.ugent.zeus.hydra.domain.Preferences;
import be.ugent.zeus.hydra.domain.entities.event.Event;
import java8.util.Comparators;
import java8.util.function.Function;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Filters events according to the user's preferences and sorts them, according to their start date.
 *
 * @author Niko Strijbol
 */
@Deprecated
public class EventFilter implements Function<List<Event>, List<Event>> {

    private final SharedPreferences preferences;

    public EventFilter(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public EventFilter(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public List<Event> apply(List<Event> events) {
        Set<String> disabled = preferences.getStringSet(Preferences.DISABLED_ASSOCIATIONS, Collections.emptySet());
        return StreamSupport.stream(events)
                .filter(e -> !disabled.contains(e.getAssociation().getInternalName()))
                .sorted(Comparators.comparing(Event::getStart))
                .collect(Collectors.toList());
    }
}