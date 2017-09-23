package webconnect.com.webconnect;

import android.util.Log;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by clickapps on 31/8/17.
 */

public enum APIExecutor {

    GET {
        @Override
        void execute(WebParam param) {
            map = (Map<String, Object>) param.requestParam;
            WebConnect.connect(IAPIService.class, param)
                    .get(param.url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    },
    GET_FILE {
        @Override
        void execute(WebParam param) {
            map = (Map<String, Object>) param.requestParam;
            WebConnect.connect(IAPIService.class, param)
                    .getFile(param.url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<ResponseBody>(param));
        }
    },
    POST {
        @Override
        void execute(WebParam param) {
            map = (Map<String, Object>) param.requestParam;
            WebConnect.connect(IAPIService.class, param)
                    .post(param.url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    },

    PUT {
        @Override
        void execute(WebParam param) {
            map = (Map<String, Object>) param.requestParam;
            WebConnect.connect(IAPIService.class, param)
                    .put(param.url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    },

    DELETE {
        @Override
        void execute(WebParam param) {
            map = (Map<String, Object>) param.requestParam;
            WebConnect.connect(IAPIService.class, param)
                    .delete(param.url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    },
    MULTIPART {
        @Override
        void execute(WebParam param) {
            Map<String, RequestBody> multipartMap = (Map<String, RequestBody>) param.multipartParam;
            WebConnect.connect(IAPIService.class, param)
                    .multipart(param.url, multipartMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    };

    Map<String, Object> map;

    abstract void execute(WebParam param);


}
