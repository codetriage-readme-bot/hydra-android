package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.UseCase;

import javax.inject.Inject;

/**
 * @author Niko Strijbol
 */
public class GetSingleAnnouncement implements UseCase<Integer, LiveData<Announcement>> {

    private final AnnouncementRepository repository;

    @Inject
    public GetSingleAnnouncement(AnnouncementRepository repository) {
        this.repository = repository;
    }

    @Override
    public LiveData<Announcement> execute(Integer id) {
        return repository.getOneLive(id);
    }
}
