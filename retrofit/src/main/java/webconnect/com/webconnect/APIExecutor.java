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
                    .get(param.pathSegment, param.pathSegmentParam, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    },
    POST {
        @Override
        void execute(WebParam param) {
            map = (Map<String, Object>) param.requestParam;
            WebConnect.connect(IAPIService.class, param)
                    .post(param.pathSegment, param.pathSegmentParam, map)
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
                    .put(param.pathSegment, param.pathSegmentParam, map)
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
                    .delete(param.pathSegment, param.pathSegmentParam, map)
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
                    .multipart(param.pathSegment, param.pathSegmentParam, multipartMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    };

    Map<String, Object> map;

    abstract void execute(WebParam param);


}
