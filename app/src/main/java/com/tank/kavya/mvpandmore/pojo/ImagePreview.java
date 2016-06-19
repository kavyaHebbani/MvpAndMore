package com.tank.kavya.mvpandmore.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya
 */
public class ImagePreview {

    @SerializedName("preview")
    private ImageItem preview;

    public ImagePreview(ImageItem preview) {
        this.preview = preview;
    }

    public ImageItem preview() {
        return preview;
    }

}
