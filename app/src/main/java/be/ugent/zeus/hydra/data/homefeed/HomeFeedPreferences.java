package be.ugent.zeus.hydra.data.homefeed;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import be.ugent.zeus.hydra.data.auth.AccountUtils;
import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.usecases.homefeed.HomeFeedOptions;
import be.ugent.zeus.hydra.ui.main.homefeed.HomeFeedFragment;
import be.ugent.zeus.hydra.utils.NetworkUtils;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import java.util.Collections;
import java.util.Set;

/**
 * @author Niko Strijbol
 */
public class HomeFeedPreferences implements HomeFeedOptions {

    private final SharedPreferences preferences;
    private final Context context;

    public HomeFeedPreferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    @Override
    public boolean isEnabled(int cardType) {
        return !getIgnoredCards().contains(cardType);
    }

    @Override
    public Set<Integer> getIgnoredCards() {
        Set<Integer> s = StreamSupport.stream(preferences
                .getStringSet(HomeFeedFragment.PREF_DISABLED_CARDS, Collections.emptySet()))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        // Don't do Minerva if there is no account.
        if (!AccountUtils.hasAccount(context)) {
            s.add(HomeCard.CardType.MINERVA_AGENDA);
            s.add(HomeCard.CardType.MINERVA_ANNOUNCEMENT);
        }

        // Don't do Urgent.fm if there is no network.
        if (!NetworkUtils.isConnected(context)) {
            s.add(HomeCard.CardType.URGENT_FM);
        }

        return s;
    }
}
