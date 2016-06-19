package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.utils.ISchedulerProvider;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Kavya
 */
class TestSchedulerProvider implements ISchedulerProvider {

    @Override
    public Scheduler mainThread() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }
}
