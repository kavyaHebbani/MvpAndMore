package com.tank.kavya.mvpandmore.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Kavya
 */
public class NetworkStatus implements INetworkStatus {

    @NonNull
    private Context mContext;

    public NetworkStatus(@NonNull Context context) {
        mContext = context;
    }

    @NonNull
    public Observable<Boolean> getIsConnectedStream() {
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
