package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.api.ApiService;
import com.tank.kavya.mvpandmore.api.INetworkStatus;
import com.tank.kavya.mvpandmore.pojo.ImageItem;
import com.tank.kavya.mvpandmore.ui.IImageViewListener;
import com.tank.kavya.mvpandmore.utils.ISchedulerProvider;

import junit.framework.Assert;

import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Kavya
 */
public final class ImageDataPresenter {

    private IImageViewListener mImageViewListener;
    private ApiService mApiService;
    private INetworkStatus mNetworkStatus;
    private ISchedulerProvider mSchedulerProvider;
    private CompositeSubscription mSubscription = new CompositeSubscription();

    public ImageDataPresenter(ApiService apiService,
                              INetworkStatus networkStatus,
                              ISchedulerProvider schedulerProvider) {
        mApiService = apiService;
        mNetworkStatus = networkStatus;
        mSchedulerProvider = schedulerProvider;
    }

    private Observable<List<ImageItem>> getImages(int pageNumber) {
        return mNetworkStatus.connectedStream()
                             .filter(it -> it)
                             .flatMap(connected -> mApiService.getImages(pageNumber));
    }

    void setListeners(IImageViewListener imageViewListener) {
        mImageViewListener = imageViewListener;
    }

    void bind() {
        Assert.assertNotNull(mImageViewListener);

        if (mSubscription.isUnsubscribed()) {
            mSubscription = new CompositeSubscription();
        }

        mSubscription.add(
                mImageViewListener.shouldFetchImages()
                                  .startWith(1)
                                  .flatMap(this::getImages)
                                  .subscribeOn(mSchedulerProvider.computation())
                                  .observeOn(mSchedulerProvider.mainThread())
                                  .subscribe(mImageViewListener::updateImages,
                                             err -> Log.e("ImageDataPresenter",
                                                          "Error updating Images:" + err)));
    }

    void unbind() {
        mSubscription.unsubscribe();
    }

}

