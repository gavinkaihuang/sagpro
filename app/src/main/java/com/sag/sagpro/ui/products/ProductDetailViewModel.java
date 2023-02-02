package com.sag.sagpro.ui.products;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;

import com.sag.sagpro.databinding.FragmentProductItemBinding;

public class ProductDetailViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String pid = null;
    private String cid = null;
    private String fid = null;
    private String name = null;
    private String img = null;
    private String content = null;
    private String other = null; //now ingore it
    private String price = null;

//    private MyProductItemRecyclerViewAdapter.ViewHolder viewHolder = null;
//
//    @Override
//    public MyProductItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        viewHolder = new MyProductItemRecyclerViewAdapter.ViewHolder(FragmentProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
//        return viewHolder;
//    }
}