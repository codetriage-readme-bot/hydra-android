package be.ugent.zeus.hydra.domain.utils;

import android.arch.lifecycle.SingleLiveData;
import android.content.SharedPreferences;

import be.ugent.zeus.hydra.domain.usecases.Executor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A LiveData that listens to preference changes. When the preferences change, a refresh will be scheduled. Since the
 * LiveData only listens when there are active observers, the LiveData will query if some monitored value has changed
 * since the last active observer, and if so, it will also schedule a refresh.
 *
 * @author Niko Strijbol
 */
public abstract class PreferenceLiveData<D> extends SingleLiveData<D> implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final SharedPreferences preferences;

    /**
     * Holds the values of the preferences as they were when the last observed change happened.
     */
    private final Map<String, Object> saved = new HashMap<>();

    /**
     * Construct a LiveData. This will schedule an initial load of the data.
     *
     * @param executor    The executor determines on which thread the data is executed.
     * @param preferences The preferences to use.
     */
    public PreferenceLiveData(Executor executor, SharedPreferences preferences) {
        super(executor);
        this.preferences = preferences;
    }

    /**
     * Get for which keys the preferences are monitored. For a better performance, this function should return a
     * collection that has a cheap {@link Collection#contains(Object)} method, as it might be called multiple times.
     * Good candidates include {@link java.util.HashSet}.
     *
     * @return The keys of the preferences to monitor.
     */
    protected abstract Collection<String> getPreferenceKeys();

    /**
     * Check if the preferences have updated or not.
     *
     * @return True if they have changed, otherwise false.
     */
    private boolean havePreferencesChanged() {
        Map<String, ?> currentValues = preferences.getAll();
        boolean hasChanged = false;
        for (String key : getPreferenceKeys()) {
            Object currentValue = currentValues.get(key);
            Object savedValue = saved.get(key);
            if (savedValue != null && !currentValue.equals(savedValue)) {
                hasChanged = true;
            }
            saved.put(key, currentValue);
        }

        return hasChanged;
    }

    @Override
    protected void onActive() {
        // Register the listener for when the settings change while it's active
        preferences.registerOnSharedPreferenceChangeListener(this);
        // Check if the value is equal to the saved value. If not, we need to reload.
        if (havePreferencesChanged()) {
            requestRefresh();
        }

        super.onActive();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (getPreferenceKeys().contains(s)) {
            saved.put(s, sharedPreferences.getAll().get(s));
            requestRefresh();
        }
    }
}