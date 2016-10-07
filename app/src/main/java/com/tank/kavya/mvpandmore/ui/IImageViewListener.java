package com.tank.kavya.mvpandmore.ui;

import com.tank.kavya.mvpandmore.pojo.ImageItem;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavya
 */
public interface IImageViewListener {

    @NonNull
    Observable<Integer> shouldFetchImages();

    void updateImages(@NonNull List<ImageItem> item);

}
