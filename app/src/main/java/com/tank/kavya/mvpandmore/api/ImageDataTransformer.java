package com.tank.kavya.mvpandmore.api;

import com.tank.kavya.mvpandmore.pojo.ImageAsset;
import com.tank.kavya.mvpandmore.pojo.ImageData;
import com.tank.kavya.mvpandmore.pojo.ImageItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Kavya
 */
class ImageDataTransformer implements Observable.Transformer<ImageData, List<ImageItem>> {

    @Override
    public Observable<List<ImageItem>> call(Observable<ImageData> imageDataObservable) {
        return imageDataObservable
                .map(ImageData::data)
                .flatMap(assets -> {
                    final List<ImageItem> items = new ArrayList<>(assets.size());
                    for (ImageAsset imageAsset : assets) {
                        items.add(imageAsset.assets().preview());
                    }
                    return Observable.just(items);
                });
    }

}
