package webconnect.com.webconnect;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * The type Retrofit util.
 */
public class RetrofitManager {
    private String BASE_URL = WebConstant.getBaseUrl();
    private static final long CONNECT_TIMEOUT_MILLIS = 10 * 1000, READ_TIMEOUT_MILLIS = 20 * 1000;
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .create();
    private OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
    private HttpLoggingInterceptor mInterceptor = new HttpLoggingInterceptor();


    /**
     * Instantiates a new Retrofit util.
     */
    protected RetrofitManager() {
        // Private Constructor
    }

    /**
     * Create service t.
     *
     * @param <T>           the type parameter
     * @param interfaceFile the interface file
     * @param webParam      the web param
     * @return the t
     */
    public <T> T createService(Class<T> interfaceFile, final WebParam webParam) {
        if (BuildConfig.DEBUG) {
            mInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        mOkHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (webParam.headerParam != null && webParam.headerParam.size() > 0) {
                    for (Map.Entry<String, String> entry : webParam.headerParam.entrySet()) {
                        request = request.newBuilder().addHeader(entry.getKey(), entry.getValue()).build();
                    }
                }
                return chain.proceed(request);
            }
        });
        mOkHttpClientBuilder.addInterceptor(mInterceptor);
        String baseUrl = BASE_URL;
        if (!TextUtils.isEmpty(webParam.baseUrl)) {
            baseUrl = webParam.baseUrl;
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        builder.client(mOkHttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(interfaceFile);
    }

    /**
     * The type Call back.
     *
     * @param <T> the type parameter
     */
    public static class CallBack<T> implements Observer<retrofit2.Response<T>> {
        private WebParam webParam;

        /**
         * Instantiates a new Call back.
         *
         * @param webParam the web param
         */
        public CallBack(WebParam webParam) {
            this.webParam = webParam;
        }

        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull retrofit2.Response<T> response) {
            String res = "";
            try {
                Object object;
                if (webParam.callback == null) {
                    return;
                }

                if (response.isSuccessful()) {
                    res = response.body().toString();
                    if (webParam.model != null) {
                        object = gson.fromJson(res, webParam.model);
                    } else {
                        object = gson.fromJson(res, Object.class);
                    }
                    webParam.callback.onSuccess(object, webParam.taskId, response);
                } else {
                    res = response.errorBody().string();
                    if (webParam.error != null) {
                        object = gson.fromJson(res, webParam.error);
                    } else {
                        object = gson.fromJson(res, Object.class);
                    }
                    webParam.callback.onError(object, res, webParam.taskId, response);

                }
            } catch (Exception e) {
                Log.e("", e.getMessage());
                webParam.callback.onError(e, res, webParam.taskId, response);
            }
        }

        @Override
        public void onError(@NonNull Throwable t) {
            try {
                if (webParam.callback != null
                        && webParam.context != null) {
                    String errors;
                    if (t.getClass().getName().contains(UnknownHostException.class.getName())) {
                        errors = webParam.context.getString(R.string.error_internet_connection);
                    } else if (t.getClass().getName().contains(TimeoutException.class.getName())
                            || t.getClass().getName().contains(SocketTimeoutException.class.getName())
                            || t.getClass().getName().contains(ConnectException.class.getName())) {
                        errors = webParam.context.getString(R.string.error_server_connection);
                    } else if (t.getClass().getName().contains(CertificateException.class.getName())) {
                        errors = webParam.context.getString(R.string.error_certificate_exception);
                    } else {
                        errors = t.getMessage();
                    }
                    webParam.callback.onError(errors, errors, webParam.taskId, null);
                }
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.e(getClass().getSimpleName(), e.getMessage());
                }
            }
        }

        @Override
        public void onComplete() {

        }
    }


    /**
     * The type String converter factory.
     */
    private static final class StringConverterFactory extends Converter.Factory {

        /**
         * Create string converter factory.
         *
         * @return the string converter factory
         */
        private static StringConverterFactory create() {
            return new StringConverterFactory();
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return new ConfigurationServiceConverter();
        }

        /**
         * The type Configuration service converter.
         */
        class ConfigurationServiceConverter implements Converter<ResponseBody, String> {

            @Override
            public String convert(ResponseBody value) throws IOException {
                return IOUtils.toString(new InputStreamReader(value.byteStream()));
            }
        }
    }
}
