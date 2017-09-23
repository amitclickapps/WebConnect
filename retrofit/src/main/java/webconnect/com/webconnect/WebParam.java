package webconnect.com.webconnect;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

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
    baseUrl, pathSegment = "", pathSegmentParam = "";
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
        DELETE,
        /**
         *
         */
        MULTIPART
    }

}
