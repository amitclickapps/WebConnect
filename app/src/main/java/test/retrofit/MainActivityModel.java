package test.retrofit;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Response;
import webconnect.com.webconnect.WebConnect;
import webconnect.com.webconnect.WebHandler;
import webconnect.com.webconnect.WebParam;

/**
 * Created by clickapps on 22/12/17.
 */

public class MainActivityModel extends AndroidViewModel implements LifecycleObserver {
    public static final String ENDPOINT_GET = "offers";
    public static final String ENDPOINT_POST = "users";
    public static final String ENDPOINT_PUT = "users/740";
    public static final String ENDPOINT_BASE = "https://reqres.in/api/";
    //    public static final String ENDPOINT_BASE = "http://api.dev.moh.clicksandbox1.com:8080/v1/";
    private Context activity;

//    private MainActivityModel(Activity activity) {
//        this.activity = activity;
//    }

    private MutableLiveData<Object> get = new MutableLiveData<>();
    private MutableLiveData<Object> post = new MutableLiveData<>();
    private MutableLiveData<Object> put = new MutableLiveData<>();
    private MutableLiveData<Object> delete = new MutableLiveData<>();

    public MainActivityModel(@NonNull Application application) {
        super(application);
        activity = application;
    }

    public LiveData<Object> getGet() {
        return get;
    }

    public LiveData<Object> getPost() {
        return post;
    }

    public LiveData<Object> getPut() {
        return put;
    }

    public LiveData<Object> getDelete() {
        return delete;
    }

    public void get() {
        WebConnect.with(this.activity, ENDPOINT_GET)
                .cache(true)
                .callback(new WebHandler.OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId, Response response) {
                        if (object != null) {
                            //Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                            get.setValue(object);
                        }
                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId) {
                        get.setValue(object);
                    }
                }).connect();
    }

    public void post() {
        Map<String, String> requestMap = new LinkedHashMap<>();
        requestMap.put("name", "Amit");
        requestMap.put("job", "manager");
        WebConnect.with(this.activity, ENDPOINT_POST)
                .httpType(WebParam.HttpType.POST)
                .baseUrl("https://reqres.in/api/test/")
                .cache(true)
                .requestParam(requestMap)
                .callback(new WebHandler.OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId, Response response) {
                        if (object != null) {
                            post.setValue(object);
                        }
                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId) {
                        post.setValue(object);
                    }
                }).connect();
    }

    public void put() {
        Map<String, String> requestMap = new LinkedHashMap<>();
        requestMap.put("name", "Amit Singh");
        requestMap.put("job", "manager");
        WebConnect.with(activity, ENDPOINT_PUT)
                .httpType(WebParam.HttpType.PUT)
                .requestParam(requestMap)
                .callback(new WebHandler.OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId, Response response) {
                        if (object != null) {
                            put.setValue(object);
                        }
                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId) {
                        put.setValue(object);
                    }
                }).connect();
    }

    public void delete() {
        Map<String, String> requestMap = new LinkedHashMap<>();
        requestMap.put("name", "Amit Singh");
        requestMap.put("job", "manager");
        WebConnect.with(activity, ENDPOINT_PUT)
                .httpType(WebParam.HttpType.PUT)
                .requestParam(requestMap)
                .callback(new WebHandler.OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId, Response response) {
                        if (object != null) {
                            delete.setValue(object);
                        }
                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId) {
                        delete.setValue(object);
                    }
                }).connect();
    }


    public static class MainActivityModelFactory implements ViewModelProvider.Factory {
        private Application activity;

        public MainActivityModelFactory(Application activity) {
            this.activity = activity;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainActivityModel(activity);
        }
    }

}
