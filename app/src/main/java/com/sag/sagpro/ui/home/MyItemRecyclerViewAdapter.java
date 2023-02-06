package com.sag.sagpro.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentHomeHeaderBinding;
import com.sag.sagpro.databinding.FragmentHomeItemBinding;
import com.sag.sagpro.databinding.FragmentHomeItemListBinding;
import com.sag.sagpro.interfaces.OnItemClickListener;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListener {

//public class MyItemRecyclerViewAdapter extends BaseBindingAdapter {

    private static final int SIGN_CATEGORY_BUTTON = 0;
    private static final int SIGN_PROUDCT_BUTTON = 1;
    private static final int SIGN_SLIDE_IMAGE = 2;
    private static final int SIGN_ROW_ITEM = 3;


    private boolean isShowHeader;
    private List<HomeItemPlaceholderItem> mValues;
    private List<String> mImages;
    Context context = null;

    public MyItemRecyclerViewAdapter(List<HomeItemPlaceholderItem> items) {
        mValues = items;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.i("------------------onCreateViewHolder");
        context = parent.getContext();
        if (isShowHeader && viewType == TYPE_HEADER)
            return new HeaderViewHolder(FragmentHomeHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        return new ItemViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    public void bindHeader(RecyclerView.ViewHolder viewHolder, int position) {
        HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        holder.viewBanner.setAdapter(new BannerImageAdapter<String>(mImages) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                Glide.with(holder.imageView)
                        .load(data)
                        .into(holder.imageView);
            }
        });
        //轮播图下面的原点
        holder.viewBanner.setIndicator(new CircleIndicator(context.getApplicationContext()));
        holder.viewBanner.setIndicatorRadius(50);

        holder.categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener == null)
                    return;
                onItemClickListener.onItemClicked(v, position, SIGN_CATEGORY_BUTTON);
            }
        });

        holder.productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener == null)
                    return;
                onItemClickListener.onItemClicked(v, position, SIGN_PROUDCT_BUTTON);
            }
        });

        holder.viewBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                if (onItemClickListener == null)
                    return;
                onItemClickListener.onItemClicked(holder.viewBanner, position, SIGN_PROUDCT_BUTTON);
            }
        });
    }

    public void bindItem(RecyclerView.ViewHolder viewHolder, int position) {
        final int realPosition = getRealPosition(viewHolder);
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        holder.mIdView.setText(mValues.get(realPosition).getCid());
        holder.mContentView.setText(mValues.get(realPosition).getName());
        holder.mNameView.setText(mValues.get(realPosition).getName());
        String imageURL = mValues.get(realPosition).getImg();

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

        //TODO imageButton 不向下传递点击动作， 暂时这样
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener == null)
                    return;

                onItemClickListener.onItemClicked(v, position, SIGN_ROW_ITEM);
            }
        });
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (getItemViewType(position) == TYPE_HEADER) {
            bindHeader(viewHolder, position);
        } else {
            bindItem(viewHolder, position);
        }
    }

    public void setItems(List<HomeItemPlaceholderItem> items) {
        this.mValues = items;
    }

    @Override
    public int getItemCount() {
        return !isShowHeader ? mValues.size() : mValues.size() + 1;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return !isShowHeader ? position : position - 1;
    }




    /**
     * Category Item
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView = null;
        public TextView mIdView;
        public TextView mContentView;
        public ImageButton imageButton;
        public TextView mNameView;

        public ItemViewHolder(FragmentHomeItemBinding binding) {
            super(binding.getRoot());
            rootView = binding.getRoot();
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            imageButton = binding.button;
            mNameView = binding.nameTextView;
            LogUtil.i("-----------ItemViewHolder binding's hashcode is " + binding.hashCode());

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    /**
     * Header
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        com.youth.banner.Banner viewBanner = null;
        ImageButton categoriesButton = null;
        ImageButton productButton = null;

        public HeaderViewHolder(FragmentHomeHeaderBinding binding) {
            super(binding.getRoot());
//            headerTextView = view.findViewById(R.id.headerTextView);
            categoriesButton = binding.categoriesButton;
            productButton = binding.productButton;
            viewBanner = binding.viewBanner;
            LogUtil.i("-----------HeaderViewHolder binding's hashcode is " + binding.hashCode());
        }

    }


    public void setIsShowHeader(boolean isShowHeader) {
        this.isShowHeader = isShowHeader;
        notifyItemInserted(0);
    }

    public int getItemViewType(int position) {
        if (!isShowHeader) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public void setmImages(List<String> mImages) {
        this.mImages = mImages;
    }


    OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

//    private void handleCategory(View view) {
//        Navigation.findNavController(view).navigate(R.id.item_navigation_categories, null);
//    }

    @Override
    public void onItemClicked(View view, int position, int sign) {
        LogUtil.i("---------item clicked " + position + " and sign=" + sign);
        switch (sign) {
            case SIGN_CATEGORY_BUTTON:
                Navigation.findNavController(view).navigate(R.id.item_navigation_categories, null);
                break;
            case SIGN_PROUDCT_BUTTON:
                Navigation.findNavController(view).navigate(R.id.item_navigation_products, null);
                break;
            case SIGN_SLIDE_IMAGE:
                Navigation.findNavController(view).navigate(R.id.item_navigation_regist, null);
                break;
            case SIGN_ROW_ITEM:
                Navigation.findNavController(view).navigate(R.id.item_navigation_regist, null);
                break;

        }
    }
}