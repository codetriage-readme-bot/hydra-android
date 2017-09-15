package be.ugent.zeus.hydra.ui.minerva.overview;

import android.view.ViewGroup;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.ui.common.ViewUtils;
import be.ugent.zeus.hydra.ui.common.recyclerview.adapters.ItemDiffAdapter;

/**
 * Adapter for announcements.
 *
 * @author Niko Strijbol
 */
class AnnouncementAdapter extends ItemDiffAdapter<Announcement, AnnouncementViewHolder> {

    @Override
    public AnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnnouncementViewHolder(ViewUtils.inflate(parent, R.layout.item_minerva_announcement));
    }
}