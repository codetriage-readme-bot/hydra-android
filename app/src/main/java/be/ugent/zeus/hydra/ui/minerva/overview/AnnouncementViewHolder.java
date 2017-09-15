package be.ugent.zeus.hydra.ui.minerva.overview;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.ui.common.recyclerview.viewholders.DataViewHolder;
import be.ugent.zeus.hydra.ui.minerva.AnnouncementActivity;
import be.ugent.zeus.hydra.utils.DateUtils;

/**
 * @author Niko Strijbol
 */
public class AnnouncementViewHolder extends DataViewHolder<Announcement> {

    private final TextView title;
    private final TextView subtitle;
    private final View clickingView;

    public AnnouncementViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        subtitle = itemView.findViewById(R.id.subtitle);
        clickingView = itemView.findViewById(R.id.clickable_view);
    }

    @Override
    public void populate(final Announcement data) {
        title.setText(data.getTitle());
        String infoText = itemView.getContext().getString(R.string.agenda_subtitle,
                DateUtils.relativeDateTimeString(data.getLastEditedAt(), itemView.getContext(), false),
                data.getLecturer());
        subtitle.setText(infoText);

        if (data.isRead()) {
            itemView.setBackgroundColor(Color.TRANSPARENT);
        } else {
            itemView.setBackgroundColor(Color.WHITE);
        }

        clickingView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AnnouncementActivity.class);
            intent.putExtra(AnnouncementActivity.ARG_ANNOUNCEMENT_ID, data.getId());
            v.getContext().startActivity(intent);
        });
    }
}