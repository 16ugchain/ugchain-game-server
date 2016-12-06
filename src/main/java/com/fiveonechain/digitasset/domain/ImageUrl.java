package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/18.
 */
public class ImageUrl {
    private int imageId;
    private int userId;
    private String url;
    private int type;

    public int getImage_id() {
        return imageId;
    }

    public void setImage_id(int imageId) {
        this.imageId = imageId;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
