package com.tank.kavya.mvpandmore.injections;

import com.tank.kavya.mvpandmore.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kavya
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);

}
