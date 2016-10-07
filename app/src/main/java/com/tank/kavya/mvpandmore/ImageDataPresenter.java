package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.api.ApiService;
import com.tank.kavya.mvpandmore.api.INetworkStatus;
import com.tank.kavya.mvpandmore.pojo.ImageItem;
import com.tank.kavya.mvpandmore.ui.IImageViewListener;
import com.tank.kavya.mvpandmore.utils.ISchedulerProvider;

import junit.framework.Assert;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Kavya
 */
public final class ImageDataPresenter {

    @NonNull
    private ApiService mApiService;

    @NonNull
    private INetworkStatus mNetworkStatus;

    @NonNull
    private ISchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscription = new CompositeSubscription();

    public ImageDataPresenter(@NonNull ApiService apiService,
                              @NonNull INetworkStatus networkStatus,
                              @NonNull ISchedulerProvider schedulerProvider) {
        mApiService = apiService;
        mNetworkStatus = networkStatus;
        mSchedulerProvider = schedulerProvider;
    }

    private Observable<List<ImageItem>> getImages(int pageNumber) {
        return mNetworkStatus.getIsConnectedStream()
                             .filter(it -> it)
                             .flatMap(connected -> mApiService.getImages(pageNumber));
    }

    void bind(@NonNull IImageViewListener imageViewListener) {
        Assert.assertNotNull(imageViewListener);

        if (mSubscription.isUnsubscribed()) {
            mSubscription = new CompositeSubscription();
        }

        mSubscription.add(imageViewListener.shouldFetchImages()
                                           .flatMap(this::getImages)
                                           .subscribeOn(mSchedulerProvider.computation())
                                           .observeOn(mSchedulerProvider.mainThread())
                                           .subscribe(imageViewListener::updateImages,
                                                      err -> Log.e("ImageDataPresenter",
                                                                   "Error updating Images:"
                                                                   + err)));
    }

    void unbind() {
        mSubscription.unsubscribe();
    }

}
