package com.feicui.zh.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/29.
 */
public class AccessTokenResult {
    @SerializedName("access_token")
    private String accesstoken;

    @SerializedName("scope")
    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccesstoken() {
        return accesstoken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}
