package be.ugent.zeus.hydra.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.activities.AssociationActivityDetail;
import be.ugent.zeus.hydra.models.Association.AssociationActivities;
import be.ugent.zeus.hydra.models.Association.AssociationActivity;

/**
 * Created by ellen on 8/3/16.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.CardViewHolder> implements StickyRecyclerHeadersAdapter {
    private ArrayList<AssociationActivity> items;

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private TextView start;
        private TextView title;
        private TextView association;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());


        public CardViewHolder(View v) {
            super(v);
            this.view = v;
            title = (TextView) v.findViewById(R.id.name);
            association = (TextView) v.findViewById(R.id.association);
            start = (TextView) v.findViewById(R.id.starttime);
        }

        public void populate(final AssociationActivity activity) {
            title.setText(activity.title);
            association.setText(activity.association.display_name);
            start.setText(dateFormatter.format(activity.start));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), AssociationActivityDetail.class);
                    intent.putExtra("associationActivity", activity);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder { //todo remove duplication
        private TextView headerText;
        private SimpleDateFormat weekFormatter = new SimpleDateFormat("w", new Locale("nl"));
        private SimpleDateFormat dayFormatter = new SimpleDateFormat("cccc", new Locale("nl"));
        private DateFormat dateFormatter = SimpleDateFormat.getDateInstance();
        private SimpleDateFormat formatter = new SimpleDateFormat(" (yyyy-MM-dd)", Locale.getDefault());//debug

        public HeaderViewHolder(View v) {
            super(v);
            headerText = (TextView) v.findViewById(R.id.resto_header_text);
        }

        public void populate(Date date) {
            headerText.setText(getFriendlyDate(date)+formatter.format(date));
        }

        private String getFriendlyDate(Date date) {
            // TODO: I feel a bit bad about all of this; any good libraries?
            // I couldn't find any that were suitable -- mivdnber
            DateTime today = new DateTime();
            DateTime dateTime = new DateTime(date);
            int thisWeek = Integer.parseInt(weekFormatter.format(today.toDate()));
            int week = Integer.parseInt(weekFormatter.format(date));

            int daysBetween = Days.daysBetween(today.toLocalDate(), dateTime.toLocalDate()).getDays();

            if (daysBetween == 0) {
                return "vandaag";
            } else if(daysBetween == 1) {
                return "morgen";
            } else if(daysBetween == 2) {
                return "overmorgen";
            } else if (week == thisWeek || daysBetween < 7) {
                return dayFormatter.format(date).toLowerCase();
            } else if (week == thisWeek + 1) {
                return "volgende " + dayFormatter.format(date).toLowerCase();
            } else {
                return dateFormatter.format(date);
            }
        }
    }

    public ActivityListAdapter() {
        this.items = new ArrayList<AssociationActivity>();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_listitem, parent, false);
        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final AssociationActivity restoCategory = items.get(position);
        holder.populate(restoCategory);
    }

    @Override
    public long getHeaderId(int position) {
        Date date = items.get(position).start;
        return date.getMonth()*100+date.getDay(); //todo make better
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_listitem_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HeaderViewHolder) holder).populate(items.get(position).start);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(AssociationActivities items) {
        this.items.clear();
        for (AssociationActivity item : items) {
            this.items.add(item);

        }


    }
}