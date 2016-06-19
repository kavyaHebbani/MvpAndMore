package com.tank.kavya.mvpandmore.utils;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kavya
 */
public final class SchedulerProvider implements ISchedulerProvider {

    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler computation() {
        return Schedulers.computation();
    }

}
