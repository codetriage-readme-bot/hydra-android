package be.ugent.zeus.hydra.domain.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.FullRepository;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public interface AnnouncementRepository extends FullRepository<Integer, Announcement> {

    LiveData<List<Announcement>> getLiveUnreadMostRecentFirst();

    List<Announcement> getUnreadMostRecentFirst();

}
