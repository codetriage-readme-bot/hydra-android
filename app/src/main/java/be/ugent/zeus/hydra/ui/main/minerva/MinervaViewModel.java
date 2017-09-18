package be.ugent.zeus.hydra.ui.main.minerva;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread;
import be.ugent.zeus.hydra.domain.usecases.minerva.GetCoursesWithUnreadCount;
import be.ugent.zeus.hydra.domain.requests.Result;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class MinervaViewModel extends AndroidViewModel {

    private final GetCoursesWithUnreadCount useCase;
    private LiveData<Result<List<CourseUnread>>> data;

    public MinervaViewModel(Application application) {
        super(application);
        this.useCase = HydraApplication.getComponent(application).getCoursesWithUnreadCount();
    }

    public LiveData<Result<List<CourseUnread>>> getData() {
        if (data == null) {
            data = Transformations.map(useCase.execute(null), Result.Builder::fromData);
        }

        return data;
    }
}