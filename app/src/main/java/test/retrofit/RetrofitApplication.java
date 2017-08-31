package test.retrofit;

import android.app.Application;

import webconnect.com.webconnect.Configuration;

/**
 * Created by clickapps on 31/8/17.
 */

public class RetrofitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Configuration.Builder()
                .baseUrl(MainActivity.ENDPOINT_BASE)
                .connectTimeOut(1000L)
                .readTimeOut(2000L)
                .config();
    }
}
