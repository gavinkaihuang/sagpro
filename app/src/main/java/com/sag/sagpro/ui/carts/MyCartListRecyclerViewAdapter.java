package com.sag.sagpro.ui.carts;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


import com.androidnetworking.widget.ANImageView;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.databinding.FragmentCartItemBinding;
import com.sag.sagpro.interfaces.NumberAdjustHandler;
import com.sag.sagpro.utils.ParamsUtils;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;
import com.sag.sagpro.widgets.ItemNoAdjustView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 *
 */
public class MyCartListRecyclerViewAdapter extends RecyclerView.Adapter<MyCartListRecyclerViewAdapter.ViewHolder> {

    private List<CartPlaceholderItem> mValues;
    private Context context = null;

    public MyCartListRecyclerViewAdapter(List<CartPlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(FragmentCartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.imgImageView.setImageUrl(holder.mItem.getImg());
        holder.nameTextView.setText(holder.mItem.getName());
        holder.numberAdjustView.setText(holder.mItem.getNumber());
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
    class CartItemNumberAdjustHandler implements NumberAdjustHandler, SingleObserver<JSONObject> {

        CartPlaceholderItem item = null;

        public CartItemNumberAdjustHandler(final CartPlaceholderItem cartPlaceholderItem) {
            item = cartPlaceholderItem;
        }

        @Override
        public void handleMinus(ItemNoAdjustView view, int result) {
            LogUtil.i("-----------CartItemNumberAdjustHandler's hashcode is " + CartItemNumberAdjustHandler.this.hashCode());
            LogUtil.i("-----------item pid=" + item.getPid());

            postUpdateCartNo(item.getCid(), item.getPrice(), result);
        }

        @Override
        public void handleAdd(ItemNoAdjustView view, int result) {
            LogUtil.i("-----------CartItemNumberAdjustHandler's hashcode is " + CartItemNumberAdjustHandler.this.hashCode());
            LogUtil.i("-----------item pid=" + item.getPid());
            postUpdateCartNo(item.getCid(), item.getPrice(), result);
        }

        //        UPDATE_CART/
        private void postUpdateCartNo(String cartId, String price, int number) {
//            data":[{"cartid":"3","price": 100.00, "number":100},{"cartid":"4","price": 100.00,"number":100},{"cartid":"5","price": 100.00,"number":100}],
            try {
                JSONObject oneItemObject = new JSONObject();
                oneItemObject.put("cartid", cartId);
                oneItemObject.put("price", price);
                oneItemObject.put("number", "" + number);
                JSONArray dataArray = new JSONArray();
                dataArray.put(oneItemObject);

                JSONObject jsonObject = ParamsUtils.getRequestParamsRoot(context);
                jsonObject.put("data", dataArray);
//                jsonObject.put("app", "android");
//                jsonObject.put("version", "1.0.0");
//                jsonObject.put("token", LoggedInUserHelper.getToken(context));
                RX2AndroidNetworkingUtils.postForData(ConstantData.UPDATE_CART, jsonObject, this);
            } catch (JSONException e) {
                LogUtil.e("-----------" + e.getMessage());
            }
        }

        @Override
        public void onSubscribe(Disposable d) {
            LogUtil.i("-----------CartItemNumberAdjustHandler onSubscribe");
        }

        @Override
        public void onSuccess(JSONObject jsonObject) {
            LogUtil.i("-----------CartItemNumberAdjustHandler onSuccess");
            LogUtil.i("-----------" + jsonObject.toString());
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.i("-----------CartItemNumberAdjustHandler onError");
        }
    }
}