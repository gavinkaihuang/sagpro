package com.sag.sagpro.ui.addresses;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sag.sagpro.databinding.FragmentAddressItemBinding;
import com.sag.sagpro.ui.addresses.placeholder.AddressPlaceholderItem;
import com.sag.sagpro.ui.placeholder.PlaceholderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AddressItemRecyclerViewAdapter extends RecyclerView.Adapter<AddressItemRecyclerViewAdapter.ViewHolder> {

    private List<PlaceholderItem> mValues;

    public AddressItemRecyclerViewAdapter() {
        mValues = new ArrayList<PlaceholderItem>();
    }

    public void setItems(List<PlaceholderItem> items) {
        mValues = items;
    }

//    public void addItem(PlaceholderItem item) {
//        mValues.add(item);
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentAddressItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AddressPlaceholderItem item = (AddressPlaceholderItem) mValues.get(position);

        holder.mItem = mValues.get(position);
        holder.nameView.setText(item.getName());
        holder.addressView.setText(item.getAddress());
        holder.phoneView.setText(item.getPhone());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView addressView;
        public final TextView phoneView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentAddressItemBinding binding) {
            super(binding.getRoot());
            nameView = binding.nameTV;
            addressView = binding.addressTV;
            phoneView = binding.phoneTV;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + addressView.getText() + "'";
        }
    }
}