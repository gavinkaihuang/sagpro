package com.sag.sagpro.ui.carts;

import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.androidnetworking.widget.ANImageView;
import com.sag.sagpro.databinding.FragmentCartItemBinding;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderItem;

import java.util.List;

/**
 *
 */
public class MyCartListRecyclerViewAdapter extends RecyclerView.Adapter<MyCartListRecyclerViewAdapter.ViewHolder> {

    private List<CartPlaceholderItem> mValues;


    public MyCartListRecyclerViewAdapter(List<CartPlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.imgImageView.setImageUrl(holder.mItem.getImg());
        holder.nameTextView.setText(holder.mItem.getName());
        holder.numberAdjustView.setText(holder.mItem.getName());
//        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setItems(List<CartPlaceholderItem> items) {
        this.mValues = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ANImageView imgImageView;
        public final TextView nameTextView;
        public CartPlaceholderItem mItem;
        com.sag.sagpro.ui.ui.ItemNoAdjustView numberAdjustView;
        public final TextView priceTextView;

        public ViewHolder(FragmentCartItemBinding binding) {
            super(binding.getRoot());
            imgImageView = binding.imgImageView;
            nameTextView = binding.nameTextView;
            numberAdjustView = binding.numberAdjustView;
            priceTextView = binding.priceTextView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameTextView.getText() + "'";
        }
    }
}