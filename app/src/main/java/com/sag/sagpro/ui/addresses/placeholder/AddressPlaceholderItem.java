package com.sag.sagpro.ui.addresses.placeholder;

import com.sag.sagpro.ui.placeholder.PlaceholderItem;

public class AddressPlaceholderItem implements PlaceholderItem {


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress1(String address1) {
        this.address = address1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public String aid;
    public String address;
    public String name;
    public String phone;
    public String choose;

    public AddressPlaceholderItem() {
    }
}
