package com.example.urldataretriever;

public class JSONPost {

    private String imgUrl;
    private String userName;

    public JSONPost (String imgUrl, String userName) {
        this.imgUrl = imgUrl;
        this.userName = userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getUserName() {
        return userName;
    }
}
