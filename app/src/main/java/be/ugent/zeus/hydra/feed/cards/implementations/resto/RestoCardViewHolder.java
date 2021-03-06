package be.ugent.zeus.hydra.feed.cards.implementations.resto;

import android.content.Intent;
import android.view.View;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.common.ui.widgets.MenuTable;
import be.ugent.zeus.hydra.feed.cards.Card;
import be.ugent.zeus.hydra.MainActivity;
import be.ugent.zeus.hydra.resto.RestoMenu;
import be.ugent.zeus.hydra.resto.network.SelectableMetaRequest;
import be.ugent.zeus.hydra.feed.HomeFeedAdapter;
import be.ugent.zeus.hydra.feed.cards.CardViewHolder;
import be.ugent.zeus.hydra.resto.menu.RestoFragment;
import be.ugent.zeus.hydra.utils.DateUtils;

/**
 * Home feed view holder for the resto menu.
 *
 * @author Niko Strijbol
 * @author feliciaan
 */
public class RestoCardViewHolder extends CardViewHolder {

    private final MenuTable table;

    public RestoCardViewHolder(View v, HomeFeedAdapter adapter) {
        super(v, adapter);
        table = v.findViewById(R.id.menu_table);
    }

    @Override
    public void populate(Card card) {
        super.populate(card);

        RestoMenuCard menuCard = card.<RestoMenuCard>checkCard(Card.Type.RESTO);
        RestoMenu menu = menuCard.getRestoMenu();
        SelectableMetaRequest.RestoChoice choice = menuCard.getRestoChoice();
        String text = itemView.getResources().getString(R.string.resto_menu_title);
        toolbar.setTitle(String.format(text, DateUtils.getFriendlyDate(menu.getDate()), choice.getName()));

        table.setMenu(menu);

        // click listener
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), MainActivity.class);
            intent.putExtra(MainActivity.ARG_TAB, R.id.drawer_resto);
            intent.putExtra(RestoFragment.ARG_DATE, menu.getDate());
            itemView.getContext().startActivity(intent);
        });
    }
}