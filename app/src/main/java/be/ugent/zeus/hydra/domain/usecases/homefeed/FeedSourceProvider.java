package be.ugent.zeus.hydra.domain.usecases.homefeed;

import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.*;

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
    private final SchamperArticleSource schamperArticleSource;
    private final EventsSource eventsSource;

    @Inject
    public FeedSourceProvider(MinervaAnnouncementsSource minervaAnnouncements, MinervaAgendaSource agenda, StallSource stall, SchamperArticleSource articleSource, EventsSource eventsSource) {
        this.minervaAnnouncements = minervaAnnouncements;
        this.minervaAgenda = agenda;
        this.stall = stall;
        this.schamperArticleSource = articleSource;
        this.eventsSource = eventsSource;
    }

    public int getCount() {
        return 5;
    }

    public List<FeedSource> getAll() {
        return Arrays.asList(eventsSource, schamperArticleSource, minervaAnnouncements, minervaAgenda, stall);
    }
}