package be.ugent.zeus.hydra.domain.usecases.minerva;

import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AnnouncementRepository;
import org.threeten.bp.ZonedDateTime;

import javax.inject.Inject;

/**
 * @author Niko Strijbol
 */
public class MarkAnnouncementAsRead implements UseCase<Announcement, Void> {

    private final AnnouncementRepository repository;

    @Inject
    public MarkAnnouncementAsRead(AnnouncementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(Announcement arguments) {
        ZonedDateTime now = ZonedDateTime.now();
        arguments.setReadAt(now);
        repository.update(arguments);
        return null;
    }
}
