package com.tank.kavya.mvpandmore.api;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Created by Kavya
 */
public interface INetworkStatus {

    @NonNull
    Observable<Boolean> getIsConnectedStream();
}
