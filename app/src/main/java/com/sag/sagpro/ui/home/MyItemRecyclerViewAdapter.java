package com.sag.sagpro.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.databinding.FragmentHomeItemBinding;
import com.sag.sagpro.ui.home.placeholder.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.i("------------------onCreateViewHolder");
//        return new ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        ViewHolder viewHolder = new ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), null, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCid());
        holder.mContentView.setText(mValues.get(position).getName());

        String imageURL = mValues.get(position).getImg();
        holder.aNImageView.setImageUrl(imageURL);
        LogUtil.i("------------------" + imageURL);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;
        public ANImageView aNImageView = null;

        public ViewHolder(FragmentHomeItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            aNImageView = binding.imageView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}