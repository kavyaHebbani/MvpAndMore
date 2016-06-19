package com.tank.kavya.mvpandmore.api;

import rx.Observable;

/**
 * Created by Kavya
 */
public interface INetworkStatus {

    Observable<Boolean> connectedStream();

}
