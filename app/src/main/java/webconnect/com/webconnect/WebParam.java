package webconnect.com.webconnect;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Web param.
 */
public class WebParam {
    /**
     * The Activity context.
     */
    protected Activity activityContext;
    /**
     * The Context.
     */
    protected Context context;
    /**
     * The Url.
     */
    protected String url, /**
     * The Base url.
     */
    baseUrl;
    /**
     * The Http type.
     */
    protected HttpType httpType = HttpType.GET;
    /**
     * The Request param.
     */
    protected Map<String, ?> requestParam = new LinkedHashMap<>();

    /**
     * The Multipart param.
     */
    protected Map<String, ?> multipartParam = new LinkedHashMap<>();
    /**
     * The Header param.
     */
    protected Map<String, String> headerParam = new LinkedHashMap<>();
    /**
     * The Callback.
     */
    protected WebHandler.OnWebCallback callback;
    /**
     * The Model.
     */
    protected Class<?> model;
    /**
     * The Error.
     */
    protected Class<?> error;
    /**
     * The Task id.
     */
    protected int taskId;

    /**
     * The enum Http type.
     */
    public enum HttpType {
        /**
         * Get http type.
         */
        GET, /**
         * Post http type.
         */
        POST, /**
         * Put http type.
         */
        PUT, /**
         * Delete http type.
         */
        DELETE
    }


    public Activity getActivityContext() {
        return activityContext;
    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpType getHttpType() {
        return httpType;
    }

    public Map<String, ?> getRequestParam() {
        return requestParam;
    }

    public Map<String, String> getHeaderParam() {
        return headerParam;
    }

    public WebHandler.OnWebCallback getCallback() {
        return callback;
    }

    public Class<?> getModel() {
        return model;
    }

    public Class<?> getError() {
        return error;
    }

    public int getTaskId() {
        return taskId;
    }

    public Map<String, ?> getMultipartParam() {
        return multipartParam;
    }

    public void setCallback(WebHandler.OnWebCallback callback) {
        this.callback = callback;
    }
}
