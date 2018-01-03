package webconnect.com.webconnect;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import java.io.File;
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
    Map<String, String> multipartParam = new LinkedHashMap<>();
    Map<String, File> multipartParamFile = new LinkedHashMap<>();
    /**
     * The Header param.
     */
    Map<String, String> headerParam = new LinkedHashMap<>();
    /**
     * The Callback.
     */
    WebHandler.OnWebCallback callback;
    /**
     * Progress during download
     */
    WebHandler.ProgressListener progressListener;

    WebHandler.AnalyticsListener analyticsListener;
    /**
     * Progress Dialog
     */
    Dialog dialog;
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

    long connectTimeOut = 0, readTimeOut = 0;
    /**
     * Download File anyType
     */
    boolean isCacheEnabled = false;
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
        PUT,
        /**
         * Patch http type.
         */
        PATCH,
        /**
         * Delete http type.
         */
        DELETE,
        /**
         * head http type.
         */
        HEAD,
        /**
         * Options http type.
         */
        OPTIONS,
        /**
         * Multipart type
         */
        MULTIPART,
        /**
         * Download
         */
        DOWNLOAD


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

    public Map<String, String> getMultipartParam() {
        return multipartParam;
    }

    public Map<String, File> getMultipartParamFile() {
        return multipartParamFile;
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

    public WebHandler.ProgressListener getProgressListener() {
        return progressListener;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public boolean isCacheEnabled() {
        return isCacheEnabled;
    }

    public WebHandler.AnalyticsListener getAnalyticsListener() {
        return analyticsListener;
    }
}
