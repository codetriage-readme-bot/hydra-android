package be.ugent.zeus.hydra.sko.studentvillage;

import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.common.ui.html.Utils;
import be.ugent.zeus.hydra.common.ui.recyclerview.viewholders.DataViewHolder;
import com.squareup.picasso.Picasso;

/**
 * @author Niko Strijbol
 */
class ExhibitorViewHolder extends DataViewHolder<Exhibitor> {

    private TextView name;
    private ImageView imageView;
    private TextView content;

    ExhibitorViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        imageView = itemView.findViewById(R.id.logo);
        content = itemView.findViewById(R.id.content);
    }

    @Override
    public void populate(final Exhibitor data) {
        name.setText(data.getName());
        Picasso.with(itemView.getContext()).load(data.getLogo()).fit().centerInside().into(imageView);

        final Spanned converted = Utils.fromHtml(data.getContent());

        content.setText(converted);
        itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(data.getName());
            builder.setMessage(converted);
            builder.setPositiveButton(R.string.ok, null);
            builder.show();
        });
    }
}