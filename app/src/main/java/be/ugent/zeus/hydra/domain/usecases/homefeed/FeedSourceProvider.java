package be.ugent.zeus.hydra.domain.usecases.homefeed;

import be.ugent.zeus.hydra.domain.usecases.homefeed.sources.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * Provides the sources for the {@link GetHomeFeed} use case.
 *
 * While this class makes it possible to filter sources, this is not recommended, because the home feed itself does
 * not remove cards when a source is deleted. Therefor it is better to have the source return an empty list when
 * disabled.
 *
 * An example of this issue: image a home feed with card types 1, 2 and 3. The home feed has been loaded. However,
 * due to some reason, card type 3 is no longer active. If you filter in this class, and trigger a refresh, the existing
 * cards of type 3 will stay in the feed. When adding the source but having the source return an empty list, existing
 * cards of type 3 will be removed from the feed.
 *
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