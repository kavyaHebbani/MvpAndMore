package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.injections.ApplicationComponent;
import com.tank.kavya.mvpandmore.injections.ApplicationModule;
import com.tank.kavya.mvpandmore.injections.DaggerApplicationComponent;

import android.app.Application;

/**
 * Created by Kavya
 */
public class MainApplication extends Application {

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDaggerComponent();
    }

    private void initializeDaggerComponent() {
        mComponent = DaggerApplicationComponent.builder()
                                               .applicationModule(new ApplicationModule(this))
                                               .build();
    }

    ApplicationComponent getComponent() {
        return mComponent;
    }

}
