package com.tank.kavya.mvpandmore.network;

import com.tank.kavya.mvpandmore.pojo.ImageItem;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavya
 */
public class ApiService {

    private static final int NUMBER_OF_IMAGES_PER_REQUEST = 10;

    @NonNull
    private IApiRequest mApiRequest;

    public ApiService(@NonNull IApiRequest apiRequest) {
        mApiRequest = apiRequest;
    }

    public Observable<List<ImageItem>> getImages(int pageNumber) {
        return mApiRequest.fetchImages(NUMBER_OF_IMAGES_PER_REQUEST, pageNumber, "full", "drum")
                          .compose(new ImageDataToPreviewTransformer());
    }

}
