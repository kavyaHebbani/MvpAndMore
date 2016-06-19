package com.tank.kavya.mvpandmore.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya
 */
public class ImageItem {

    @SerializedName("url")
    private String imageUrl;

    public ImageItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String imageUrl() {
        return imageUrl;
    }

}
