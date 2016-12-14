package com.fiveonechain.digitasset.domain.result;

import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserInfo;

import java.util.List;

/**
 * Created by fanjl on 2016/12/12.
 */
public class UserInfoCmd {
    private User user;
    private UserInfo userInfo;
    private List<String> imageUrl;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
