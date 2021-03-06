package be.ugent.zeus.hydra.common.caching;

import android.os.Bundle;
import android.support.annotation.NonNull;
import be.ugent.zeus.hydra.common.request.Result;
import be.ugent.zeus.hydra.common.request.CacheableRequest;

/**
 * @author Niko Strijbol
 */
public class TestRequest implements CacheableRequest<TestObject> {

    private boolean read;

    private final long cache;
    private final String key;

    public TestRequest(long cache, String key) {
        this.cache = cache;
        this.key = key;
    }

    @NonNull
    @Override
    public Result<TestObject> performRequest(Bundle args) {
        read = true;
        return Result.Builder.fromData(new TestObject());
    }

    @NonNull
    @Override
    public String getCacheKey() {
        return key;
    }

    @Override
    public long getCacheDuration() {
        return cache;
    }

    public boolean isRead() {
        return read;
    }

    public void reset() {
        read = false;
    }
}
