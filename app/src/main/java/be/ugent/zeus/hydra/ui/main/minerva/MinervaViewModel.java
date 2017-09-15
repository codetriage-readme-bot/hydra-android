package be.ugent.zeus.hydra.ui.main.minerva;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.data.database.minerva2.RepositoryFactory;
import be.ugent.zeus.hydra.domain.usecases.minerva.CourseRepository;
import be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread;
import be.ugent.zeus.hydra.repository.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class MinervaViewModel extends AndroidViewModel {

    private LiveData<Result<List<CourseUnread>>> data;

    public MinervaViewModel(Application application) {
        super(application);
    }

    public LiveData<Result<List<CourseUnread>>> getData() {
        if (data == null) {
            CourseRepository repository = RepositoryFactory.getCourseDatabaseRepository(getApplication());
            data = Transformations.map(repository.getAllAndUnreadInOrder(), Result.Builder::fromData);
        }

        return data;
    }
}