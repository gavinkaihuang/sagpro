package com.sag.sagpro.ui.home;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.databinding.FragmentHomeItemBinding;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.youth.banner.Banner;

import java.util.List;

/**
 *
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>  {

//public class MyItemRecyclerViewAdapter extends BaseBindingAdapter {
    private List<HomeItemPlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<HomeItemPlaceholderItem> items) {
        mValues = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.i("------------------onCreateViewHolder");
//        return new ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        ViewHolder viewHolder = new ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCid());
        holder.mContentView.setText(mValues.get(position).getName());
        holder.mNameView.setText(mValues.get(position).getName());
        String imageURL = mValues.get(position).getImg();
//        holder.aNImageView.setImageUrl(imageURL);
        LogUtil.i("------------------onBindViewHolder " + imageURL);

        AndroidNetworkingUtils.loadImageFromURL(imageURL, "HomeListImage", new ImageLoadCallback() {
            @Override
            public Bitmap loadImageSucceed(Bitmap bitmap) {
                if (bitmap != null) {
                    Drawable drawable = new BitmapDrawable(bitmap);
//                    holder.imageButton.setBackground(drawable);
//                    holder.imageButton.setImageBitmap(bitmap);
                    holder.imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                    holder.imageButton.setBackground(drawable);
//                    holder.imageButton.setBackground();
                }
                return null;
            }

            @Override
            public Exception loadImageFailed(Exception exception) {
                return null;
            }
        });

//        LogUtil.i("------------------onBindViewHolder ");
//        FragmentHomeItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
//        binding.setItem(mValues.get(position));
//        binding.executePendingBindings();


    }

    public void setItems(List<HomeItemPlaceholderItem> items) {
        this.mValues = items;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView = null;
        public  TextView mIdView;
        public  TextView mContentView;
        public ImageButton imageButton;
        public TextView mNameView;
//        public Banner viewBanner;
//        public PlaceholderItem mItem;
//        public ANImageView aNImageView = null;

        public ViewHolder(FragmentHomeItemBinding binding) {
            super(binding.getRoot());
            rootView = binding.getRoot();
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            imageButton = binding.button;
            mNameView = binding.nameTextView;
//            viewBanner = binding.v
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}