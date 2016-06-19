package com.tank.kavya.mvpandmore.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Kavya
 */
public class NetworkStatus implements INetworkStatus {

    private Context mContext;

    public NetworkStatus(Context context) {
        mContext = context;
    }

    public Observable<Boolean> connectedStream() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                    final BroadcastReceiver br = new BroadcastReceiver() {
                        @Override
                        public void onReceive(final Context context, final Intent intent) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(getIsConnected());
                            }
                        }
                    };
                    mContext.registerReceiver(br, intentFilter);
                    subscriber.add(Subscriptions.create(() -> mContext.unregisterReceiver(br)));
                }
            }
        }).distinctUntilChanged().observeOn(Schedulers.computation());
    }

    private boolean getIsConnected() {
        NetworkInfo networkInfo = getNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private NetworkInfo getNetworkInfo() {
        return ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
    }

}
