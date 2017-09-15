package be.ugent.zeus.hydra.data.database.minerva2;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

/**
 * @author Niko Strijbol
 */
public class NullAwareTransformations {

    public static <X, Y> LiveData<Y> map(LiveData<X> source, Function<X, Y> function) {
        return Transformations.map(source, x -> x == null ? null : function.apply(x));
    }

}
