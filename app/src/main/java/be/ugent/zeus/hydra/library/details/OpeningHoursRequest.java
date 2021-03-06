package be.ugent.zeus.hydra.library.details;

import android.support.annotation.NonNull;

import be.ugent.zeus.hydra.common.network.Endpoints;
import be.ugent.zeus.hydra.common.network.JsonSpringRequest;
import be.ugent.zeus.hydra.common.caching.Cache;
import be.ugent.zeus.hydra.common.request.CacheableRequest;
import be.ugent.zeus.hydra.library.Library;

/**
 * Get the opening hours for one library.
 *
 * @author Niko Strijbol
 */
class OpeningHoursRequest extends JsonSpringRequest<OpeningHours[]> implements CacheableRequest<OpeningHours[]> {

    private final String libraryCode;

    OpeningHoursRequest(Library library) {
        super(OpeningHours[].class);
        this.libraryCode = library.getCode();
    }

    @NonNull
    @Override
    public String getCacheKey() {
        return libraryCode + "_opening.json";
    }

    @Override
    public long getCacheDuration() {
        return Cache.ONE_DAY * 4;
    }

    @NonNull
    @Override
    protected String getAPIUrl() {
        return Endpoints.LIBRARY_URL + "libraries/" + libraryCode + "/calendar.json";
    }
}