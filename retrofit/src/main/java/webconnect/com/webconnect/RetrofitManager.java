package webconnect.com.webconnect;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * The type Retrofit util.
 */
public class RetrofitManager {
    private OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
    private HttpLoggingInterceptor mInterceptor = new HttpLoggingInterceptor();
    private static OkHttpClient.Builder mOkHttpClientBuilderTemp = new OkHttpClient.Builder();


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
    protected <T> T createService(Class<T> interfaceFile, final WebParam webParam) {
        mInterceptor.setLevel(ApiConfiguration.isDebug() ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        mOkHttpClientBuilder.connectTimeout(webParam.connectTimeOut == 0 ? ApiConfiguration.getConnectTimeOut() : webParam.connectTimeOut, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.readTimeout(webParam.readTimeOut == 0 ? ApiConfiguration.getReadTimeOut() : webParam.connectTimeOut, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (webParam.headerParam != null && webParam.headerParam.size() > 0) {
                    for (Map.Entry<String, String> entry : webParam.headerParam.entrySet()) {
                        request = request.newBuilder().addHeader(entry.getKey(), entry.getValue()).build();
                    }
                }
                Response originalResponse = chain.proceed(request);
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), webParam))
                        .build();
            }
        });
        mOkHttpClientBuilder.addInterceptor(mInterceptor);
        mOkHttpClientBuilderTemp = mOkHttpClientBuilder;
        String baseUrl = ApiConfiguration.getBaseUrl();
        if (!TextUtils.isEmpty(webParam.baseUrl)) {
            baseUrl = webParam.baseUrl;
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(ApiConfiguration.getGson()));
        builder.client(mOkHttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        if (webParam.dialog != null &&
                !webParam.dialog.isShowing()) {
            webParam.dialog.show();
        }
        return retrofit.create(interfaceFile);
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
         * The type ApiConfiguration service converter.
         */
        class ConfigurationServiceConverter implements Converter<ResponseBody, String> {

            @Override
            public String convert(ResponseBody value) throws IOException {
                return IOUtils.toString(new InputStreamReader(value.byteStream()));
            }
        }
    }

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final WebParam webParam;
        private BufferedSource bufferedSource;

        ProgressResponseBody(ResponseBody responseBody, WebParam webParam) {
            this.responseBody = responseBody;
            this.webParam = webParam;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    if (webParam.progressListener != null)
                        webParam.progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }


    static void download(final WebParam param) {
        Request request = new Request.Builder()
                .url(param.url)
                .build();
        mOkHttpClientBuilderTemp.build().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException t) {
                try {
                    if (param.dialog != null &&
                            param.dialog.isShowing()) {
                        param.dialog.dismiss();
                    }
                    if (param.callback != null
                            && param.context != null) {
                        final String errors;
                        if (t.getClass().getName().contains(UnknownHostException.class.getName())) {
                            errors = param.context.getString(R.string.error_internet_connection);
                        } else if (t.getClass().getName().contains(TimeoutException.class.getName())
                                || t.getClass().getName().contains(SocketTimeoutException.class.getName())
                                || t.getClass().getName().contains(ConnectException.class.getName())) {
                            errors = param.context.getString(R.string.error_server_connection);
                        } else if (t.getClass().getName().contains(CertificateException.class.getName())) {
                            errors = param.context.getString(R.string.error_certificate_exception);
                        } else {
                            errors = t.toString();
                        }
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                param.callback.onError(errors, errors, param.taskId);
                            }
                        });
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    if (BuildConfig.DEBUG) {
                        Log.e(getClass().getSimpleName(), e.getMessage());
                    }
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            param.callback.onError(e.getMessage(), e.getMessage(), param.taskId);
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Object object = null;
                if (param.dialog != null &&
                        param.dialog.isShowing()) {
                    param.dialog.dismiss();
                }
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    OutputStream out = null;
                    object = param.file;
                    try {
                        out = new FileOutputStream(param.file);
                        IOUtils.copy(body.byteStream(), out);
                    } finally {
                        IOUtils.closeQuietly(out);
                    }
                    Handler h = new Handler(Looper.getMainLooper());
                    final Object finalObject = object;
                    h.post(new Runnable() {
                        public void run() {
                            param.callback.onSuccess(finalObject, param.taskId, null);
                        }
                    });

                }
            }
        });
    }
}
