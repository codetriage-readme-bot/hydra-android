package be.ugent.zeus.hydra.data.cache;

import android.support.annotation.NonNull;
import android.util.Log;

import java8.util.Optional;

import java.io.*;

/**
 * Cache executor that uses default Java serialization to write/read objects.
 *
 * On Android, the default serializer is not fast. However, for the current use in the application (save some 'smaller'
 * data), it is sufficient. It is also executed in a background thread, so worst case scenario, the user has to wait a
 * little longer for the data (ns or ms). The alternative would be to use an external serializer library
 * (such as fst[1]). This makes the app take up a lot more space, so we do not do that currently.
 * If profiling suggests the serialisation here is really the bottleneck, which is unlikely since
 * it is about network requests, we can easily switch to fst.
 *
 * @author Niko Strijbol
 */
class SerializableExecutor implements CacheExecutor {

    private static final String TAG = "SerializableExecutor";

    private final File directory;

    /**
     * @param directory The cache directory, where the data should be saved.
     */
    SerializableExecutor(File directory) {
        this.directory = directory;
    }

    @Override
    public <D extends Serializable> void save(String key, CacheObject<D> data) throws CacheException {
        //TODO try with resources
        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(new FileOutputStream(new File(directory, key)));
            stream.writeObject(data);
        } catch (IOException e) {
            throw new CacheException(e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                Log.w(TAG, "Error while closing stream.", e);
            }
        }
    }

    @NonNull
    @Override
    public <D extends Serializable> Optional<CacheObject<D>> read(String key) throws CacheException {
        //TODO try with resources
        ObjectInputStream stream = null;
        try {
            // Where the cache should be.
            File file = new File(directory, key);
            if (file.exists()) {
                // There is no entry for this key.
                Log.i(TAG, "No cache entry exists for key " + key);
                return Optional.empty();
            }
            stream = new ObjectInputStream(new FileInputStream(file));
            //noinspection unchecked - this is normal
            return Optional.of((CacheObject<D>) stream.readObject());
        } catch (ClassNotFoundException | IOException | ClassCastException e) {
            // Throw any error we might get.
            throw new CacheException(e);
        } finally {
            // Try to close the stream.
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                // Ignore this.
                Log.w(TAG, "Error while closing stream.", e);
            }
        }
    }

    @Override
    public boolean delete(String key) {
        File file = new File(directory, key);
        return !file.isFile() || file.delete();
    }
}