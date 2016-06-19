package com.tank.kavya.mvpandmore.api;

import com.tank.kavya.mvpandmore.pojo.ImageItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavya
 */
public class ApiService {

    private static final int NUMBER_OF_IMAGES_PER_REQUEST = 10;

    private IApiRequest mApiRequest;

    public ApiService(IApiRequest apiRequest) {
        mApiRequest = apiRequest;
    }

    public Observable<List<ImageItem>> getImages(int pageNumber) {
        return mApiRequest.fetchImages(NUMBER_OF_IMAGES_PER_REQUEST, pageNumber, "full", "drum")
                          .compose(new ImageDataTransformer());
    }

}
