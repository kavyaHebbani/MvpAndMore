package com.tank.kavya.mvpandmore.utils;

import rx.Scheduler;

/**
 * Created by Kavya
 */
public interface ISchedulerProvider {

    Scheduler mainThread();

    Scheduler computation();

}
