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

    private final List<FeedSource> sources;

    @Inject
    public FeedSourceProvider(MinervaAnnouncementsSource minervaAnnouncements,
                              MinervaAgendaSource minervaAgenda,
                              StallSource stall,
                              SchamperArticleSource schamperArticles,
                              EventsSource eventsSource) {
        sources = Arrays.asList(eventsSource, schamperArticles, minervaAnnouncements, minervaAgenda, stall);
    }

    public int getCount() {
        return sources.size();
    }

    public List<FeedSource> getAll() {
        return sources;
    }
}