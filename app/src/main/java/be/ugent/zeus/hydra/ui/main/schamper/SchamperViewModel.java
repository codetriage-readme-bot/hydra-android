package be.ugent.zeus.hydra.ui.main.schamper;

import android.app.Application;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.domain.entities.SchamperArticle;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.schamper.GetSchamperArticles;
import be.ugent.zeus.hydra.domain.utils.livedata.LiveDataInterface;
import be.ugent.zeus.hydra.ui.common.BetterRefreshViewModel;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public class SchamperViewModel extends BetterRefreshViewModel<List<SchamperArticle>> {

    private final GetSchamperArticles useCase;

    public SchamperViewModel(Application application) {
        super(application);
        useCase = HydraApplication.getComponent(application).getSchamperArticles();
    }

    @Override
    protected LiveDataInterface<Result<List<SchamperArticle>>> executeUseCase() {
        return useCase.execute(null);
    }
}