package webconnect.com.webconnect;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.util.Map;


/**
 * The type Builder.
 */
public class Builder {


    private WebParam mWebParam;

    /**
     * Instantiates a new Builder.
     *
     * @param context the context
     * @param url     the url
     */
    public Builder(@NonNull Activity context, @NonNull String url) {
        mWebParam = new WebParam();
        mWebParam.activityContext = context;
        mWebParam.context = context;
        mWebParam.url = url;
    }

    /**
     * Instantiates a new Builder.
     *
     * @param context the context
     * @param url     the url
     */
    public Builder(@NonNull Context context, @NonNull String url) {
        mWebParam = new WebParam();
        mWebParam.context = context;
        mWebParam.url = url;
    }


    /**
     * Base url builder.
     *
     * @param url the url
     * @return the builder
     */
    public Builder baseUrl(@NonNull String url) {
        mWebParam.baseUrl = url;
        return this;
    }

    /**
     * Http type builder.
     *
     * @param httpType the http type
     * @return the builder
     */
    public Builder httpType(@NonNull WebParam.HttpType httpType) {
        mWebParam.httpType = httpType;
        return this;
    }

    /**
     * Request param builder.
     *
     * @param requestParam the request param
     * @return the builder
     */
    public Builder requestParam(@NonNull Map<String, ?> requestParam) {
        mWebParam.requestParam = requestParam;
        return this;
    }

    /**
     * Request param builder.
     *
     * @param multipartParam the multipart Param
     * @return the builder
     */
    public Builder multipartParam(@NonNull Map<String, ?> multipartParam) {
        mWebParam.multipartParam = multipartParam;
        return this;
    }

    /**
     * Header param builder.
     *
     * @param headerParam the header param
     * @return the builder
     */
    public Builder headerParam(@NonNull Map<String, String> headerParam) {
        mWebParam.headerParam = headerParam;
        return this;
    }

    /**
     * Callback builder.
     *
     * @param callback the callback
     * @return the builder
     */
    public Builder callback(@NonNull WebHandler.OnWebCallback callback) {
        mWebParam.callback = callback;
        return this;
    }

    /**
     * Callback builder.
     *
     * @param callback the callback
     * @param success  the success
     * @param error    the error
     * @return the builder
     */
    public Builder callback(@NonNull WebHandler.OnWebCallback callback,
                            @NonNull Class<?> success, @NonNull Class<?> error) {
        mWebParam.callback = callback;
        mWebParam.model = success;
        mWebParam.error = error;
        return this;
    }

    /**
     * Success model builder.
     *
     * @param success the success
     * @return the builder
     */
    public Builder successModel(@NonNull Class<?> success) {
        mWebParam.model = success;
        return this;
    }

    /**
     * Error model builder.
     *
     * @param error the error
     * @return the builder
     */
    public Builder errorModel(@NonNull Class<?> error) {
        mWebParam.error = error;
        return this;
    }

    /**
     * Task id builder.
     *
     * @param taskId the task id
     * @return the builder
     */
    public Builder taskId(int taskId) {
        mWebParam.taskId = taskId;
        return this;
    }


    public WebParam getWebParam() {
        return mWebParam;
    }

    /**
     * Connect t.
     *
     * @param <T> the type parameter
     * @param cls the cls
     * @return the t
     */
    public <T> T connect(Class<T> cls) {
        return new RetrofitManager().createService(cls, mWebParam);
    }
}
