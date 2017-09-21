package be.ugent.zeus.hydra.domain.usecases.homefeed;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;

import java.util.Set;

/**
 * @author Niko Strijbol
 */
public interface HomeFeedOptions {

    /**
     * Returns true if the given card should be enabled.
     *
     * @param cardType The type of card to check.
     *
     * @return True if enabled, false otherwise.
     */
    boolean isEnabled(@HomeCard.CardType int cardType);

    Set<Integer> getIgnoredCards();

    /**
     * @return True if there is a network connection, false otherwise.
     */
    boolean isConnectedToNetwork();

    /**
     * @return True if there currently is a Minerva account, false otherwise.
     */
    boolean hasMinervaAccount();
}