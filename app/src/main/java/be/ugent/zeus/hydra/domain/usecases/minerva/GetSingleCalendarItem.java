package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.AgendaItemRepository;

import javax.inject.Inject;

/**
 * @author Niko Strijbol
 */
public class GetSingleCalendarItem implements UseCase<Integer, LiveData<AgendaItem>> {

    private final AgendaItemRepository agendaItemRepository;

    @Inject
    public GetSingleCalendarItem(AgendaItemRepository repository) {
        this.agendaItemRepository = repository;
    }

    @Override
    public LiveData<AgendaItem> execute(Integer id) {
        return agendaItemRepository.getOneLive(id);
    }
}