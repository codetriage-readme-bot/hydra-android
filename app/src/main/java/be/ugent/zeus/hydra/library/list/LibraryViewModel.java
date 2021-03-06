package be.ugent.zeus.hydra.library.list;

import android.app.Application;

import be.ugent.zeus.hydra.common.arch.data.BaseLiveData;
import be.ugent.zeus.hydra.common.request.Result;
import be.ugent.zeus.hydra.common.ui.RefreshViewModel;
import be.ugent.zeus.hydra.library.Library;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class LibraryViewModel extends RefreshViewModel<List<Library>> {

    public LibraryViewModel(Application application) {
        super(application);
    }

    @Override
    protected BaseLiveData<Result<List<Library>>> constructDataInstance() {
        return new LibraryLiveData(getApplication());
    }
}