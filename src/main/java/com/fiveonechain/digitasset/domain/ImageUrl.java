package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/18.
 */
public class ImageUrl {
    private int imageId;
    private int userId;
    private String url;
    private int type;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
