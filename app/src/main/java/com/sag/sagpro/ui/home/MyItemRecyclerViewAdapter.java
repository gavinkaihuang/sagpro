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
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentHomeHeaderBinding;
import com.sag.sagpro.databinding.FragmentHomeItemBinding;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.youth.banner.Banner;

import java.util.List;

/**
 *
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

//public class MyItemRecyclerViewAdapter extends BaseBindingAdapter {


    private View mHeaderView;
    private List<HomeItemPlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<HomeItemPlaceholderItem> items) {
        mValues = items;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.i("------------------onCreateViewHolder");

        if(mHeaderView != null && viewType == TYPE_HEADER)
            return new HeaderViewHolder(mHeaderView);

//        return new ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        ItemViewHolder itemViewHolder = new ItemViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        return itemViewHolder;

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_HEADER) return;

//        final int pos = getRealPosition(viewHolder);

        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        holder.mIdView.setText(mValues.get(position).getCid());
        holder.mContentView.setText(mValues.get(position).getName());
        holder.mNameView.setText(mValues.get(position).getName());
        String imageURL = mValues.get(position).getImg();

        AndroidNetworkingUtils.loadImageFromURL(imageURL, "HomeListImage", new ImageLoadCallback() {
            @Override
            public Bitmap loadImageSucceed(Bitmap bitmap) {
                if (bitmap != null) {
                    Drawable drawable = new BitmapDrawable(bitmap);
                    holder.imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                    holder.imageButton.setBackground(drawable);
                }
                return null;
            }

            @Override
            public Exception loadImageFailed(Exception exception) {
                return null;
            }
        });

    }

    public void setItems(List<HomeItemPlaceholderItem> items) {
        this.mValues = items;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mValues.size() : mValues.size() + 1;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView = null;
        public  TextView mIdView;
        public  TextView mContentView;
        public ImageButton imageButton;
        public TextView mNameView;
//        public Banner viewBanner;
//        public PlaceholderItem mItem;
//        public ANImageView aNImageView = null;

        public ItemViewHolder(FragmentHomeItemBinding binding) {
//            if (headerBinding != null)  {
//                super(headerBinding.getRoot());
//            } else {
                super(binding.getRoot());
                rootView = binding.getRoot();
                mIdView = binding.itemNumber;
                mContentView = binding.content;
                imageButton = binding.button;
                mNameView = binding.nameTextView;
//            }

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView headerTextView;

        public HeaderViewHolder(View view) {
            super(view);
            headerTextView = view.findViewById(R.id.headerTextView);
        }
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
}