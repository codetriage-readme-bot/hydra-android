package be.ugent.zeus.hydra.domain.usecases.homefeed;

import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.MinervaAgendaSource;
import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.MinervaAnnouncementsSource;
import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.SchamperArticleSource;
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
    private final SchamperArticleSource schamperArticleSource;

    @Inject
    public FeedSourceProvider(MinervaAnnouncementsSource minervaAnnouncements, MinervaAgendaSource agenda, StallSource stall, SchamperArticleSource articleSource) {
        this.minervaAnnouncements = minervaAnnouncements;
        this.minervaAgenda = agenda;
        this.stall = stall;
        this.schamperArticleSource = articleSource;
    }

    public int getCount() {
        return 4;
    }

    public List<FeedSource> getAll() {
        return Arrays.asList(schamperArticleSource, minervaAnnouncements, minervaAgenda, stall);
    }
}