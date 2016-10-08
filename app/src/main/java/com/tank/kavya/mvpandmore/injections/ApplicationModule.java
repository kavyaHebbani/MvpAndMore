package com.tank.kavya.mvpandmore.injections;

import com.tank.kavya.mvpandmore.ImageDataPresenter;
import com.tank.kavya.mvpandmore.network.ApiService;
import com.tank.kavya.mvpandmore.network.IApiRequest;
import com.tank.kavya.mvpandmore.network.INetworkStatus;
import com.tank.kavya.mvpandmore.network.NetworkStatus;
import com.tank.kavya.mvpandmore.utils.ISchedulerProvider;
import com.tank.kavya.mvpandmore.utils.SchedulerProvider;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kavya
 */
@Module
public class ApplicationModule {

    private static final String CLIENT_ID = "cc8eda3d67155fda58f8";

    private static final String CLIENT_SECRET = "2d88458a248813fca7da997eeecabf01275acb6c";

    private static final String BASE_URL = "https://" + CLIENT_ID + ":" + CLIENT_SECRET
                                           + "@api.shutterstock.com/";

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @Named("ForApplication")
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ImageDataPresenter provideImageDataPresenter(ApiService apiService,
                                                 INetworkStatus networkStatus,
                                                 ISchedulerProvider schedulerProvider) {
        return new ImageDataPresenter(apiService, networkStatus, schedulerProvider);
    }

    @Provides
    @Singleton
    INetworkStatus provideNetworkStatus(@Named("ForApplication") Context context) {
        return new NetworkStatus(context);
    }

    @Provides
    @Singleton
    ISchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

    @Provides
    @Singleton
    ApiService provideApiRequest(IApiRequest IApiRequest) {
        return new ApiService(IApiRequest);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    IApiRequest provideApiService(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(IApiRequest.class);
    }

}
