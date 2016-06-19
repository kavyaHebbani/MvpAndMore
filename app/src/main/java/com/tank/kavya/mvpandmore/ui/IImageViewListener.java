package com.tank.kavya.mvpandmore.ui;

import com.tank.kavya.mvpandmore.pojo.ImageItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavya
 */
public interface IImageViewListener {

    Observable<Integer> shouldFetchImages();

    void updateImages(List<ImageItem> item);

}
