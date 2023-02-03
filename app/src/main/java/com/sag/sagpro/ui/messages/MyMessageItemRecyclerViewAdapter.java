package com.sag.sagpro.ui.messages;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentMessageItemBinding;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMessageItemRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageItemRecyclerViewAdapter.ViewHolder> {

    private List<MessagePlaceholderItem> mValues;

    public MyMessageItemRecyclerViewAdapter(List<MessagePlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMessageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
        holder.mItem = mValues.get(position);
        holder.titleTextView.setText(holder.mItem.title);
        holder.contentTextView.setText(holder.mItem.content);
        holder.createdTextView.setText(holder.mItem.created);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;
        public TextView createdTextView;
        public MessagePlaceholderItem mItem;

        public ViewHolder(FragmentMessageItemBinding binding) {
            super(binding.getRoot());
            titleTextView = binding.titleTextView;
            contentTextView = binding.contentTextView;
            createdTextView = binding.createdTextView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleTextView.getText() + "'";
        }
    }

    public void setItems(List<MessagePlaceholderItem> items) {
        this.mValues = items;
    }
}