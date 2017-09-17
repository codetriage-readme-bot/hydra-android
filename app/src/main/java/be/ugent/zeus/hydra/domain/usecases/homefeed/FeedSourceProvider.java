package be.ugent.zeus.hydra.domain.usecases.homefeed;

import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.MinervaAgendaSource;
import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.MinervaAnnouncementsSource;
import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.StallSource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@Singleton
public class FeedSourceProvider {

    private final MinervaAnnouncementsSource minervaAnnouncements;
    private final MinervaAgendaSource minervaAgenda;
    private final StallSource stall;

    @Inject
    public FeedSourceProvider(MinervaAnnouncementsSource minervaAnnouncements, MinervaAgendaSource agenda, StallSource stall) {
        this.minervaAnnouncements = minervaAnnouncements;
        this.minervaAgenda = agenda;
        this.stall = stall;
    }

    public int getCount() {
        return 3;
    }

    public List<FeedSource> getAll() {
        return Arrays.asList(stall, minervaAnnouncements, minervaAgenda);
    }
}