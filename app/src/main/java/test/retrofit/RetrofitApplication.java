package test.retrofit;

import android.app.Application;
import android.arch.lifecycle.LifecycleRegistry;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import webconnect.com.webconnect.ApiConfiguration;

/**
 * Created by clickapps on 31/8/17.
 */

public class RetrofitApplication extends Application  {


    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        new ApiConfiguration.Builder()
                .baseUrl(MainActivityModel.ENDPOINT_BASE)
                .timeOut(1000L, 2000L)
                .debug(true)
                .config();
    }
}
