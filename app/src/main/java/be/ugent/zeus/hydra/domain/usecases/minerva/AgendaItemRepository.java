package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.usecases.FullRepository;
import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public interface AgendaItemRepository extends FullRepository<Integer, AgendaItem> {

    LiveData<List<AgendaItem>> getAllForCourse(String courseId, boolean onlyFuture);

}
