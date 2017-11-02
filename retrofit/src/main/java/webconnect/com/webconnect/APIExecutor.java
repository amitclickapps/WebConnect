package webconnect.com.webconnect;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

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
        void execute(final WebParam param) {
            WebConnect.connect(IAPIService.class, param);
            RetrofitManager.download(param);

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

    PATCH {
        @Override
        void execute(WebParam param) {
            map = (Map<String, Object>) param.requestParam;
            WebConnect.connect(IAPIService.class, param)
                    .patch(param.url, map)
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
