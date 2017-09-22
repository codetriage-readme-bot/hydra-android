package be.ugent.zeus.hydra.domain.usecases.event;

import android.content.SharedPreferences;

import be.ugent.zeus.hydra.domain.cache.Cache;
import be.ugent.zeus.hydra.domain.entities.event.Event;
import be.ugent.zeus.hydra.domain.requests.Request;
import be.ugent.zeus.hydra.domain.requests.Requests;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.Executor;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.utils.CancelableRefreshLiveDataImpl;
import be.ugent.zeus.hydra.domain.utils.PreferenceLiveData;
import be.ugent.zeus.hydra.domain.utils.RefreshLiveData;
import be.ugent.zeus.hydra.ui.preferences.AssociationSelectPrefActivity;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * @author Niko Strijbol
 */
public class GetEvents implements UseCase<Void, RefreshLiveData<Result<List<Event>>>> {

    private final Executor executor;
    private final Request<Event[]> request;
    private final SharedPreferences preferences;

    @Inject
    public GetEvents(Cache cache, @Named(Executor.BACKGROUND) Executor executor, Request.Cacheable<Event[]> request, SharedPreferences preferences) {
        this.executor = executor;
        this.preferences = preferences;
        this.request = Requests.cache(cache, request);
    }

    @Override
    public RefreshLiveData<Result<List<Event>>> execute(Void arguments) {
        // TODO: this syntax is ugly, maybe add a builder?
        return new PreferenceLiveData<Result<List<Event>>>(executor, (companion, bundle) -> CancelableRefreshLiveDataImpl.CancelableResult.completed(request.performRequest(bundle)
                .map(Arrays::asList)
                .map(new EventFilter(preferences))), preferences) {

            private Set<String> disabledAssociations;

            @Override
            protected Collection<String> getPreferenceKeys() {
                return Collections.singleton(AssociationSelectPrefActivity.PREF_ASSOCIATIONS_SHOWING);
            }

            @Override
            protected boolean havePreferencesChanged(SharedPreferences preferences) {
                Set<String> current = preferences.getStringSet(AssociationSelectPrefActivity.PREF_ASSOCIATIONS_SHOWING, Collections.emptySet());
                boolean hasChanged = disabledAssociations != null && !current.equals(disabledAssociations);
                disabledAssociations = current;
                return hasChanged;
            }
        };
    }
}