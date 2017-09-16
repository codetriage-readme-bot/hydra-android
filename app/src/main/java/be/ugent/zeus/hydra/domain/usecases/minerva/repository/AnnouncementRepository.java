package be.ugent.zeus.hydra.domain.usecases.minerva.repository;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.usecases.FullRepository;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public interface AnnouncementRepository extends FullRepository<Integer, Announcement> {

    LiveData<List<Announcement>> getLiveUnreadMostRecentFirst();

    LiveData<List<Announcement>> getLiveMostRecentFirst(String courseId);

    List<Announcement> getUnreadMostRecentFirst();
}
