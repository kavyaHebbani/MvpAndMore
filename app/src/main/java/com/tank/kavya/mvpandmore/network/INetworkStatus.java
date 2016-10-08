package com.tank.kavya.mvpandmore.network;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Created by Kavya
 */
public interface INetworkStatus {

    @NonNull
    Observable<Boolean> getIsConnectedStream();
}
