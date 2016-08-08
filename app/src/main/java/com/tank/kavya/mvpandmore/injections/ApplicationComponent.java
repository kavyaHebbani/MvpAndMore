package com.tank.kavya.mvpandmore.injections;

import com.tank.kavya.mvpandmore.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kavya
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainFragment fragment);

}
