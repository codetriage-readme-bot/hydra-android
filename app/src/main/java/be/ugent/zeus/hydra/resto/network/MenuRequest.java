package be.ugent.zeus.hydra.resto.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import be.ugent.zeus.hydra.resto.RestoMenu;
import be.ugent.zeus.hydra.common.network.Endpoints;
import be.ugent.zeus.hydra.common.network.JsonSpringRequest;
import be.ugent.zeus.hydra.common.caching.Cache;
import be.ugent.zeus.hydra.common.request.CacheableRequest;
import be.ugent.zeus.hydra.resto.RestoPreferenceFragment;

/**
 * Request for the menu's of the resto's.
 *
 * @author mivdnber
 */
public class MenuRequest extends JsonSpringRequest<RestoMenu[]> implements CacheableRequest<RestoMenu[]> {

    @VisibleForTesting
    public static final String OVERVIEW_URL = Endpoints.ZEUS_RESTO_URL + "menu/%s/overview.json";

    private final SharedPreferences preferences;

    public MenuRequest(Context context) {
        super(RestoMenu[].class);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    @NonNull
    @Override
    public String getCacheKey() {
        String resto = preferences.getString(RestoPreferenceFragment.PREF_RESTO_KEY, RestoPreferenceFragment.PREF_DEFAULT_RESTO);
        return "menuOverview_" + resto + ".json";
    }

    @NonNull
    @Override
    protected String getAPIUrl() {
        String resto = preferences.getString(RestoPreferenceFragment.PREF_RESTO_KEY, RestoPreferenceFragment.PREF_DEFAULT_RESTO);
        return String.format(OVERVIEW_URL, resto);
    }

    @Override
    public long getCacheDuration() {
        return Cache.ONE_HOUR * 12;
    }
}