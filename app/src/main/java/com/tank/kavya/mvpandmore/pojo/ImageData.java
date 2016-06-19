package com.tank.kavya.mvpandmore.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kavya
 */
public class ImageData {

    @SerializedName("data")
    private List<ImageAsset> data = new ArrayList<>();

    public ImageData(List<ImageAsset> data) {
        this.data = data;
    }

    public Collection<ImageAsset> data() {
        return data;
    }

}
