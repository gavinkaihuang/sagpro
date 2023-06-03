package com.sag.sagpro.ui.orders.placeholder;

import com.sag.sagpro.ui.placeholder.PlaceholderItem;

public class OrderPlaceholderItem implements PlaceholderItem {

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getPaystatuslabel() {
        return paystatuslabel;
    }

    public void setPaystatuslabel(String paystatuslabel) {
        this.paystatuslabel = paystatuslabel;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String orderid;
    public String st;
    public String paystatus;
    public String paystatuslabel;
    public String created;
    public String amount;
    public String remark;

    public OrderPlaceholderItem() {
    }
}
