package be.ugent.zeus.hydra.domain.utils;

import android.content.SharedPreferences;
import android.os.Bundle;

import be.ugent.zeus.hydra.domain.usecases.Executor;
import java8.util.function.BiFunction;
import java8.util.function.Function;

import java.util.Collection;

/**
 * @author Niko Strijbol
 */
public abstract class PreferenceLiveData<D> extends CancelableRefreshLiveDataImpl<D> implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final SharedPreferences preferences;

    /**
     * Construct a Live Data.
     *  @param executor   The executor determines on which thread the data is executed.
     * @param executable The code to execute.
     * @param preferences
     */
    public PreferenceLiveData(Executor executor, BiFunction<Executor.Companion, Bundle, CancelableResult<D>> executable, SharedPreferences preferences) {
        super(executor, executable);
        this.preferences = preferences;
    }

    protected abstract Collection<String> getPreferenceKeys();

    protected abstract boolean havePreferencesChanged(SharedPreferences preferences);

    @Override
    protected void onActive() {
        // Register the listener for when the settings change while it's active
        preferences.registerOnSharedPreferenceChangeListener(this);
        // Check if the value is equal to the saved value. If not, we need to reload.
        if (havePreferencesChanged(preferences)) {
            requestRefresh();
        }

        super.onActive();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (getPreferenceKeys().contains(s)) {
            requestRefresh();
        }
    }

    @Override
    public <E> RefreshLiveData<E> map(Function<D, E> function) {
        return new PreferenceLiveData<E>(executor, (companion, bundle) -> executable.apply(companion, bundle).map(function), preferences) {
            @Override
            protected Collection<String> getPreferenceKeys() {
                return PreferenceLiveData.this.getPreferenceKeys();
            }

            @Override
            protected boolean havePreferencesChanged(SharedPreferences preferences) {
                return PreferenceLiveData.this.havePreferencesChanged(preferences);
            }
        };
    }
}