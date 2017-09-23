package webconnect.com.webconnect;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    protected <T> T createService(Class<T> interfaceFile, final WebParam webParam) {
        mInterceptor.setLevel(ApiConfiguration.isDebug() ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        mOkHttpClientBuilder.connectTimeout(ApiConfiguration.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.readTimeout(ApiConfiguration.getReadTimeOut(), TimeUnit.MILLISECONDS);
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
}
