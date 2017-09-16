package be.ugent.zeus.hydra.domain.usecases.homefeed;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;

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
}