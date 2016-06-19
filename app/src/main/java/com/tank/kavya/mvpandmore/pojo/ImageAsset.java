package com.tank.kavya.mvpandmore.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya
 */
public class ImageAsset {

    @SerializedName("assets")
    private ImagePreview assets;

    public ImageAsset(ImagePreview assets) {
        this.assets = assets;
    }

    public ImagePreview assets() {
        return assets;
    }

}
