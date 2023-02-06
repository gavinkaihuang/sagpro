package com.sag.sagpro.ui.home;

public class LoopImageBean {

    public String getCid() {
        return cid;
    }

    public String getImage() {
        return image;
    }

    public String getCname() {
        return cname;
    }

    private String cid = null;
    private String cname = null;
    private String image = null;

    public LoopImageBean(String cid, String cname,  String image) {
        this.cid = cid;
        this.cname = cname;
        this.image = image;
    }
}
