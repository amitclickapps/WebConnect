package webconnect.com.webconnect;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Web param.
 */
public class WebParam implements Serializable {
    /**
     * The Activity context.
     */
    Activity activityContext;
    /**
     * The Context.
     */
    Context context;
    /**
     * The Url.
     */
    String url, /**
     * The Base url.
     */
    baseUrl;
    /**
     * The Http type.
     */
    HttpType httpType = HttpType.GET;
    /**
     * The Request param.
     */
    Map<String, ?> requestParam = new LinkedHashMap<>();

    /**
     * The Multipart param.
     */
    Map<String, ?> multipartParam = new LinkedHashMap<>();
    /**
     * The Header param.
     */
    Map<String, String> headerParam = new LinkedHashMap<>();
    /**
     * The Callback.
     */
    WebHandler.OnWebCallback callback;
    /**
     * The Model.
     */
    Class<?> model;
    /**
     * The Error.
     */
    Class<?> error;
    /**
     * The Task id.
     */
    int taskId;
    /**
     * Download File anyType
     */
    boolean isFile;
    File file;

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

    public Map<String, ?> getMultipartParam() {
        return multipartParam;
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

    public File getFile() {
        return file;
    }

    public boolean isFile() {
        return isFile;
    }
}
