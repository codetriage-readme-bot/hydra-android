package be.ugent.zeus.hydra.common.ui.recyclerview.adapters;

import be.ugent.zeus.hydra.common.ui.recyclerview.viewholders.DataViewHolder;

/**
 * Adapter designed to work with a {@link DataViewHolder}.
 *
 * @author Niko Strijbol
 */
@Deprecated
public abstract class ItemAdapter<D, V extends DataViewHolder<D>> extends Adapter<D, V> {

    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.populate(items.get(position));
    }
}