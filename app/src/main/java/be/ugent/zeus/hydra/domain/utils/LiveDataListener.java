package be.ugent.zeus.hydra.domain.utils;

/**
 * @author Niko Strijbol
 */
public interface LiveDataListener<D> {

    public void postValue(D data);

}
