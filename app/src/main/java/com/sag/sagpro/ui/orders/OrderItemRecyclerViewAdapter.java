package com.sag.sagpro.ui.orders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sag.sagpro.databinding.FragmentAddressItemBinding;
import com.sag.sagpro.databinding.FragmentOrderItemBinding;
import com.sag.sagpro.ui.addresses.placeholder.AddressPlaceholderItem;
import com.sag.sagpro.ui.orders.placeholder.OrderPlaceholderItem;
import com.sag.sagpro.ui.placeholder.PlaceholderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OrderItemRecyclerViewAdapter extends RecyclerView.Adapter<OrderItemRecyclerViewAdapter.ViewHolder> {

    private List<PlaceholderItem> mValues;

    public OrderItemRecyclerViewAdapter() {
        mValues = new ArrayList<PlaceholderItem>();
    }

    public void setItems(List<PlaceholderItem> items) {
        mValues = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentOrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        OrderPlaceholderItem item = (OrderPlaceholderItem) mValues.get(position);

        holder.mItem = mValues.get(position);
        holder.remarkTV.setText(item.getRemark());
        holder.paystatuslabelTV.setText(item.getPaystatuslabel());
        holder.createdTV.setText(item.getCreated());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public PlaceholderItem getItem(int position) {
        return mValues.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView remarkTV;
        public final TextView paystatuslabelTV;
        public final TextView createdTV;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentOrderItemBinding binding) {
            super(binding.getRoot());
            remarkTV = binding.remarkTV;
            paystatuslabelTV = binding.paystatuslabelTV;
            createdTV = binding.createdTV;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + remarkTV.getText() + "'";
        }
    }
}