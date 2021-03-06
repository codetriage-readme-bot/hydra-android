package be.ugent.zeus.hydra.feed.commands;

import android.content.Context;

import be.ugent.zeus.hydra.feed.cards.Card;
import be.ugent.zeus.hydra.feed.cards.CardDismissal;
import be.ugent.zeus.hydra.feed.cards.CardIdentifier;
import be.ugent.zeus.hydra.feed.cards.CardRepository;
import org.junit.Test;
import org.threeten.bp.Instant;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Niko Strijbol
 */
public class DisableIndividualCardTest {

    @Test
    public void testExecuteAndUndo() {
        // TODO: this relies a bit much on the internals.
        CardRepository repository = new MemoryCardRepository();
        CardIdentifier identifier = new CardIdentifier(Card.Type.DEBUG, "test");
        CardDismissal cardDismissal = new CardDismissal(identifier, Instant.now());
        Context context = mock(Context.class);

        DisableIndividualCard disableIndividualCard = new DisableIndividualCard(cardDismissal, c -> repository);
        disableIndividualCard.execute(context);

        List<CardDismissal> dismissals = repository.getForType(Card.Type.DEBUG);
        assertEquals(1, dismissals.size());
        assertEquals(identifier.getIdentifier(), dismissals.get(0).getIdentifier().getIdentifier());

        disableIndividualCard.undo(context);

        List<CardDismissal> newDismissals = repository.getForType(Card.Type.DEBUG);
        assertTrue(newDismissals.isEmpty());
    }
}