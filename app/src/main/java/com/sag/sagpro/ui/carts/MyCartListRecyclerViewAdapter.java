package com.sag.sagpro.ui.carts;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


import com.androidnetworking.widget.ANImageView;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.databinding.FragmentCartItemBinding;
import com.sag.sagpro.interfaces.NumberAdjustHandler;
import com.sag.sagpro.widgets.ItemNoAdjustView;

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
        holder.priceTextView.setText(holder.mItem.getPrice());
        holder.numberAdjustView.setNumberAdjustHandler(new CartItemNumberAdjustHandler(holder.mItem));
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
        ItemNoAdjustView numberAdjustView;
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

    /**
     * Handle user click minus or add button
     */
    class CartItemNumberAdjustHandler implements NumberAdjustHandler {

        CartPlaceholderItem item = null;
        public CartItemNumberAdjustHandler(final CartPlaceholderItem cartPlaceholderItem) {
            item = cartPlaceholderItem;
        }

        @Override
        public void handleMinus(ItemNoAdjustView view, int result) {
            LogUtil.i("-----------CartItemNumberAdjustHandler's hashcode is " + CartItemNumberAdjustHandler.this.hashCode());
            LogUtil.i("-----------item pid=" + item.getPid());
        }

        @Override
        public void handleAdd(ItemNoAdjustView view, int result) {
            LogUtil.i("-----------CartItemNumberAdjustHandler's hashcode is " + CartItemNumberAdjustHandler.this.hashCode());
            LogUtil.i("-----------item pid=" + item.getPid());

        }
    }
}