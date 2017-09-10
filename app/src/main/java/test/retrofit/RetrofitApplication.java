package test.retrofit;

import android.app.Application;

import webconnect.com.webconnect.ApiConfiguration;

/**
 * Created by clickapps on 31/8/17.
 */

public class RetrofitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new ApiConfiguration.Builder()
                .baseUrl(MainActivity.ENDPOINT_BASE)
                .timeOut(1000L, 2000L)
                .debug(false)
                .config();
    }
}
