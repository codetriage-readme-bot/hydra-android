package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.UseCase;

import javax.inject.Inject;
import java.util.List;

/**
 * Get all unread announcements for a course with a given ID. They are sorted by date, descending. This means the most
 * recent announcements are first.
 *
 * This use case runs async, meaning you don't need to run it in a separate thread.
 *
 * @author Niko Strijbol
 */
public class GetAnnouncementsForCourse implements UseCase<String, LiveData<List<Announcement>>> {

    private final AnnouncementRepository repository;

    @Inject
    public GetAnnouncementsForCourse(AnnouncementRepository repository) {
        this.repository = repository;
    }

    @Override
    public LiveData<List<Announcement>> execute(String id) {
        return repository.getLiveMostRecentFirst(id);
    }
}