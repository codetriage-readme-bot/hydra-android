package be.ugent.zeus.hydra.domain.usecases.minerva;

import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AnnouncementRepository;
import org.threeten.bp.ZonedDateTime;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

/**
 * Mark on or more announcements as read.
 *
 * This use-case is executed synchronously.
 *
 * @author Niko Strijbol
 */
public class MarkAnnouncementAsRead implements UseCase<Collection<Announcement>, Void> {

    private final AnnouncementRepository repository;

    @Inject
    public MarkAnnouncementAsRead(AnnouncementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(Collection<Announcement> announcements) {
        ZonedDateTime now = ZonedDateTime.now();
        // Set the read date for every announcement.
        for (Announcement announcement: announcements) {
            announcement.setReadAt(now);
        }
        // Update in the database.
        repository.update(announcements);
        return null;
    }

    /**
     * Shortcut for {@link #execute(Collection)} with a single announcement.
     *
     * @param announcement The announcement to mark as read.
     */
    public void execute(Announcement announcement) {
        this.execute(Collections.singleton(announcement));
    }
}