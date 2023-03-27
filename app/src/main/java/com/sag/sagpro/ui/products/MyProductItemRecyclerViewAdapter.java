package com.sag.sagpro.ui.products;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentProductItemBinding;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderItem;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ProductPlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProductItemRecyclerViewAdapter extends RecyclerView.Adapter<MyProductItemRecyclerViewAdapter.ViewHolder> {

    private List<ProductPlaceholderItem> mValues;

    public MyProductItemRecyclerViewAdapter(List<ProductPlaceholderItem> items) {
        mValues = items;
    }

    private ViewHolder viewHolder = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(FragmentProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.imageView.setDefaultImageResId(R.drawable.image_load_default);
        holder.imageView.setErrorImageResId(R.drawable.img_error);
        holder.imageView.setImageUrl(mValues.get(position).getImg());
        holder.nameTextView.setText(mValues.get(position).getName());
        holder.descriptionTextView.setText(mValues.get(position).getRemark());
        holder.priceTextView.setText("$" + mValues.get(position).getPrice());
//        holder.priceTextView.setText(mValues.get(position).getRemark());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView = null;
        public ANImageView imageView;
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView priceTextView;
        public ImageView addToCartButton;

        public ViewHolder(FragmentProductItemBinding binding) {
            super(binding.getRoot());
            rootView = binding.getRoot();
            imageView = binding.imageView;
            nameTextView = binding.nameTextView;
            descriptionTextView = binding.descriptionTextView;
            priceTextView = binding.priceTextView;
            addToCartButton = binding.addToCartButton;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameTextView.getText() + "'";
        }
    }

    public void setItems(List<ProductPlaceholderItem> items) {
        this.mValues = items;
    }
}