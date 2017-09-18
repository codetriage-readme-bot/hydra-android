package be.ugent.zeus.hydra.data.database.minerva2;

import android.content.Context;

import be.ugent.zeus.hydra.data.database.minerva2.announcement.DatabaseAnnouncementRepository;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AnnouncementRepository;

/**
 * @author Niko Strijbol
 */
@Deprecated
public final class RepositoryFactory {

    private static DatabaseAnnouncementRepository announcementRepository;

    public static synchronized AnnouncementRepository getAnnouncementDatabaseRepository(Context context) {
        if (announcementRepository == null) {
            announcementRepository = new DatabaseAnnouncementRepository(context);
        }
        return announcementRepository;
    }
}