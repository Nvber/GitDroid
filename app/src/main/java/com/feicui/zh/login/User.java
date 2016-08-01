package com.feicui.zh.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/29.
 */
public class User {
    // 登录所用的账号
    private String login;
    //用户名
    private String name;

    private String id;
    //用户头像路径
    @SerializedName("avatar_url")
    private String avatar;

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
