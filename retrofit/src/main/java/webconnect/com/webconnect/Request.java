package webconnect.com.webconnect;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import webconnect.com.webconnect.di.IProperties;

/**
 * Created by clickapps on 27/12/17.
 */
@SuppressWarnings({"unchecked"})
public class Request<T extends Request> {
    private WebParam param;

    public WebParam getParam() {
        return param;
    }

    public Request(GetRequestBuilder builder) {
        param = builder.param;
    }

    public Request(PostRequestBuilder builder) {
        param = builder.param;
    }

    public Request(DownloadBuilder builder) {
        param = builder.param;
    }

    public Request(MultiPartBuilder builder) {
        param = builder.param;
    }

    public Observable<?> execute() {
        return new GetRequestBuilder<>(param).execute();
    }

    public static class GetRequestBuilder<T extends GetRequestBuilder> implements IProperties {
        private WebParam param;
        private OkHttpClient okHttpClient;

        public GetRequestBuilder(WebParam param) {
            this.param = param;
        }

        @Override
        public T baseUrl(@NonNull String url) {
            param.url = url;
            return (T) this;
        }

        @Override
        public T headerParam(@NonNull Map<String, String> headerParam) {
            param.headerParam = headerParam;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback) {
            param.callback = callback;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback, @NonNull Class<?> success, @NonNull Class<?> error) {
            param.callback = callback;
            param.model = success;
            param.error = error;
            return (T) this;
        }

        @Override
        public T taskId(int taskId) {
            param.taskId = taskId;
            return (T) this;
        }

        @Override
        public T timeOut(long connectTimeOut, long readTimeOut) {
            param.connectTimeOut = connectTimeOut;
            param.readTimeOut = readTimeOut;
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(param.readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .build();
            return (T) this;
        }

        @Override
        public T progressDialog(Dialog dialog) {
            param.dialog = dialog;
            return (T) this;
        }

        @Override
        public T cache(boolean isCache) {
            param.isCacheEnabled = isCache;
            return (T) this;
        }

        public T queryParam(@NonNull Map<String, String> requestParam) {
            param.requestParam = requestParam;
            return (T) this;
        }

        public Request build() {
            return new Request(this);
        }

        @Override
        public void connect() {
            execute().subscribe(new Callback.GetRequestCallback(param));
//            IAPIService request = RetrofitManager.get().createService(param);
//            if (param.httpType == WebParam.HttpType.GET) {
//                request.get(param.url, (Map<String, Object>) param.requestParam)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Callback.RequestCallback<>(param));
//            } else if (param.httpType == WebParam.HttpType.HEAD) {
//                request.head(param.url, (Map<String, Object>) param.requestParam)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Callback.RequestCallback<>(param));
//            } else if (param.httpType == WebParam.HttpType.OPTIONS) {
//                request.options(param.url, (Map<String, Object>) param.requestParam)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Callback.RequestCallback<>(param));
//            }
        }


        public Observable<?> execute() {
            String baseUrl = param.baseUrl;
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.getBaseUrl();
            }
            Rx2ANRequest.GetRequestBuilder getBuilder;
            switch (param.httpType) {
                case GET:
                    getBuilder = Rx2AndroidNetworking.get(baseUrl + param.url);
                    break;
                case HEAD:
                    getBuilder = Rx2AndroidNetworking.head(baseUrl + param.url);
                    break;
                case OPTIONS:
                    getBuilder = Rx2AndroidNetworking.options(baseUrl + param.url);
                    break;
                default:
                    getBuilder = Rx2AndroidNetworking.get(baseUrl + param.url);
                    break;
            }
            getBuilder.addQueryParameter(param.requestParam)
                    .addHeaders(param.headerParam);
            if (okHttpClient != null) {
                getBuilder.setOkHttpClient(okHttpClient);
            }
            getBuilder = param.isCacheEnabled ? getBuilder.getResponseOnlyIfCached() : getBuilder.getResponseOnlyFromNetwork();
            return getBuilder.setTag(param.taskId)
                    .build()
                    .getObjectObservable(param.model == null ? String.class : param.model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public static class PostRequestBuilder<T extends PostRequestBuilder> implements IProperties {
        private WebParam param;
        private OkHttpClient okHttpClient;

        public PostRequestBuilder(WebParam param) {
            this.param = param;
        }

        @Override
        public T baseUrl(@NonNull String url) {
            param.url = url;
            return (T) this;
        }

        @Override
        public T headerParam(@NonNull Map<String, String> headerParam) {
            param.headerParam = headerParam;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback) {
            param.callback = callback;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback, @NonNull Class<?> success, @NonNull Class<?> error) {
            param.callback = callback;
            param.model = success;
            param.error = error;
            return (T) this;
        }

        @Override
        public T taskId(int taskId) {
            param.taskId = taskId;
            return (T) this;
        }

        @Override
        public T timeOut(long connectTimeOut, long readTimeOut) {
            param.connectTimeOut = connectTimeOut;
            param.readTimeOut = readTimeOut;
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(param.readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .build();
            return (T) this;
        }

        @Override
        public T progressDialog(Dialog dialog) {
            param.dialog = dialog;
            return (T) this;
        }

        @Override
        public T cache(boolean isCache) {
            param.isCacheEnabled = isCache;
            return (T) this;
        }

        public T bodyParam(@NonNull Map<String, ?> requestParam) {
            param.requestParam = requestParam;
            return (T) this;
        }

        public T addFile(@NonNull File file) {
            param.file = file;
            return (T) this;
        }

        public Request build() {
            return new Request(this);
        }

        @Override
        public void connect() {
            execute().subscribe(new Callback.PostRequestCallback(param));
        }

        public Observable<?> execute() {
            String baseUrl = param.baseUrl;
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.getBaseUrl();
            }
            Rx2ANRequest.PostRequestBuilder postBuilder;
            switch (param.httpType) {
                case POST:
                    postBuilder = Rx2AndroidNetworking.post(baseUrl + param.url);
                    break;
                case PUT:
                    postBuilder = Rx2AndroidNetworking.put(baseUrl + param.url);
                    break;
                case DELETE:
                    postBuilder = Rx2AndroidNetworking.delete(baseUrl + param.url);
                    break;
                case PATCH:
                    postBuilder = Rx2AndroidNetworking.patch(baseUrl + param.url);
                    break;
                default:
                    postBuilder = Rx2AndroidNetworking.post(baseUrl + param.url);
                    break;
            }
            postBuilder.addBodyParameter(param.requestParam)
                    .addHeaders(param.headerParam);
            if (param.file != null) {
                postBuilder.addFileBody(param.file);
            }
            if (okHttpClient != null) {
                postBuilder.setOkHttpClient(okHttpClient);
            }
            postBuilder = param.isCacheEnabled ? postBuilder.getResponseOnlyIfCached() : postBuilder.getResponseOnlyFromNetwork();
            return postBuilder.setTag(param.taskId)
                    .build()
                    .getObjectObservable(param.model == null ? Object.class : param.model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    }

    public static class DownloadBuilder<T extends DownloadBuilder> implements IProperties {
        private WebParam param;
        private OkHttpClient okHttpClient;

        public DownloadBuilder(WebParam param) {
            this.param = param;
        }

        @Override
        public T baseUrl(@NonNull String url) {
            param.url = url;
            return (T) this;
        }

        @Override
        public T headerParam(@NonNull Map<String, String> headerParam) {
            param.headerParam = headerParam;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback) {
            param.callback = callback;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback, @NonNull Class<?> success, @NonNull Class<?> error) {
            param.callback = callback;
            param.model = success;
            param.error = error;
            return (T) this;
        }

        @Override
        public T taskId(int taskId) {
            param.taskId = taskId;
            return (T) this;
        }

        @Override
        public T timeOut(long connectTimeOut, long readTimeOut) {
            param.connectTimeOut = connectTimeOut;
            param.readTimeOut = readTimeOut;
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(param.readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .build();
            return (T) this;
        }

        @Override
        public T progressDialog(Dialog dialog) {
            param.dialog = dialog;
            return (T) this;
        }

        @Override
        public T cache(boolean isCache) {
            param.isCacheEnabled = isCache;
            return (T) this;
        }

        public T queryParam(@NonNull Map<String, String> requestParam) {
            param.requestParam = requestParam;
            return (T) this;
        }

        public T progressListener(@NonNull WebHandler.ProgressListener callback) {
            param.progressListener = callback;
            return (T) this;
        }

        public T file(@NonNull File file) {
            param.file = file;
            return (T) this;
        }

        public Request build() {
            return new Request(this);
        }

        @Override
        public void connect() {
            String baseUrl = param.baseUrl;
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.getBaseUrl();
            }
            Rx2ANRequest.DownloadBuilder downloadBuilder = Rx2AndroidNetworking.download(baseUrl + param.url, param.file.getPath(), param.file.getName());
            downloadBuilder.addQueryParameter(param.requestParam)
                    .setTag(param.taskId)
                    .addHeaders(param.headerParam);
            if (okHttpClient != null) {
                downloadBuilder.setOkHttpClient(okHttpClient);
            }
            Rx2ANRequest okHttpRequest = downloadBuilder.build();
            okHttpRequest.setDownloadProgressListener(new Callback.ProgressCallback(param));
            okHttpRequest.startDownload(new Callback.DownloadRequestCallback(param));
        }
    }

    public static class MultiPartBuilder<T extends MultiPartBuilder> implements IProperties {
        private WebParam param;

        public MultiPartBuilder(WebParam param) {
            this.param = param;
        }

        @Override
        public T baseUrl(@NonNull String url) {
            param.url = url;
            return (T) this;
        }

        @Override
        public T headerParam(@NonNull Map<String, String> headerParam) {
            param.headerParam = headerParam;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback) {
            param.callback = callback;
            return (T) this;
        }

        @Override
        public T callback(@NonNull WebHandler.OnWebCallback callback, @NonNull Class<?> success, @NonNull Class<?> error) {
            param.callback = callback;
            param.model = success;
            param.error = error;
            return (T) this;
        }

        @Override
        public T taskId(int taskId) {
            param.taskId = taskId;
            return (T) this;
        }

        @Override
        public T timeOut(long connectTimeOut, long readTimeOut) {
            param.connectTimeOut = connectTimeOut;
            param.readTimeOut = readTimeOut;
            return (T) this;
        }

        @Override
        public T progressDialog(Dialog dialog) {
            param.dialog = dialog;
            return (T) this;
        }

        @Override
        public T cache(boolean isCache) {
            param.isCacheEnabled = isCache;
            return (T) this;
        }

        public T multipartParam(@NonNull Map<String, String> multipartParam) {
            param.multipartParam = multipartParam;
            return (T) this;
        }

        public T multipartParamFile(@NonNull Map<String, File> multipartFile) {
            param.multipartParamFile = multipartFile;
            return (T) this;
        }

        public T progressListener(@NonNull WebHandler.ProgressListener callback) {
            param.progressListener = callback;
            return (T) this;
        }

        public Request build() {
            return new Request(this);
        }

        @Override
        public void connect() {
            execute().subscribe(new Callback.UploadRequestCallback(param));
        }

        public Observable<?> execute() {
            String baseUrl = param.baseUrl;
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.getBaseUrl();
            }
            Rx2ANRequest.MultiPartBuilder multipartBuilder = Rx2AndroidNetworking.upload(baseUrl + param.url);
            return multipartBuilder.addHeaders(param.headerParam)
                    .addMultipartFile(param.multipartParamFile)
                    .addMultipartParameter(param.multipartParam)
                    .setTag(param.taskId)
                    .build()
                    .getObjectObservable(param.model == null ? Object.class : param.model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    }
}
