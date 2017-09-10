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
    private Activity activityContext;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Url.
     */
    private String url, /**
     * The Base url.
     */
    baseUrl, pathSegment = "", pathSegmentParam = "";
    /**
     * The Http type.
     */
    private HttpType httpType = HttpType.GET;
    /**
     * The Request param.
     */
    private Map<String, ?> requestParam = new LinkedHashMap<>();

    /**
     * The Multipart param.
     */
    private Map<String, ?> multipartParam = new LinkedHashMap<>();
    /**
     * The Header param.
     */
    private Map<String, String> headerParam = new LinkedHashMap<>();
    /**
     * The Callback.
     */
    private WebHandler.OnWebCallback callback;
    /**
     * The Model.
     */
    private Class<?> model;
    /**
     * The Error.
     */
    private Class<?> error;
    /**
     * The Task id.
     */
    private int taskId;

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

    public String getPathSegment() {
        return pathSegment;
    }

    public String getPathSegmentParam() {
        return pathSegmentParam;
    }

    public static class Builder {
        private WebParam webParam;

        public Builder(@NonNull Activity context, @NonNull String url) {
            webParam = new WebParam();
            webParam.activityContext = context;
            webParam.context = context;
            webParam.url = url;
        }

        public Builder(@NonNull Context context, @NonNull String url) {
            webParam = new WebParam();
            webParam.context = context;
            webParam.url = url;
        }

        public Builder(@NonNull Context context, @NonNull String url, @NonNull String pathSegment) {
            webParam = new WebParam();
            webParam.context = context;
            webParam.url = url;
            webParam.pathSegment = pathSegment;
        }


        public Builder baseUrl(@NonNull String url) {
            webParam.baseUrl = url;
            return this;
        }

        public Builder pathSegment(@NonNull String pathSegment) {
            webParam.pathSegment = pathSegment;
            return this;
        }

        public Builder pathSegmentParam(@NonNull String pathSegmentParam) {
            webParam.pathSegmentParam = pathSegmentParam;
            return this;
        }

        public Builder httpType(@NonNull WebParam.HttpType httpType) {
            webParam.httpType = httpType;
            return this;
        }

        public Builder requestParam(@NonNull Map<String, ?> requestParam) {
            webParam.requestParam = requestParam;
            return this;
        }

        public Builder multipartParam(@NonNull Map<String, ?> multipartParam) {
            webParam.multipartParam = multipartParam;
            return this;
        }

        public Builder headerParam(@NonNull Map<String, String> headerParam) {
            webParam.headerParam = headerParam;
            return this;
        }

        public Builder callback(@NonNull WebHandler.OnWebCallback callback) {
            webParam.callback = callback;
            return this;
        }

        public Builder callback(@NonNull WebHandler.OnWebCallback callback,
                                @NonNull Class<?> success, @NonNull Class<?> error) {
            webParam.callback = callback;
            webParam.model = success;
            webParam.error = error;
            return this;
        }

        public Builder successModel(@NonNull Class<?> success) {
            webParam.model = success;
            return this;
        }

        public Builder errorModel(@NonNull Class<?> error) {
            webParam.error = error;
            return this;
        }

        public Builder taskId(int taskId) {
            webParam.taskId = taskId;
            return this;
        }

        @Deprecated
        public WebParam build() {
            return webParam;
        }

        /**
         * To use this have to set these values
         * <p>
         * httpType(HttpType) <br/>
         * pathSegment(String) <br/>
         * pathSegment(String)<br/>
         * <p>
         *
         * @see #httpType(HttpType)
         * @see #pathSegment(String)
         * @see #pathSegmentParam(String)
         */
        public void connect() {
            if (webParam.httpType == HttpType.GET) {
                APIExecutor.GET.execute(webParam);
            } else if (webParam.httpType == HttpType.POST) {
                APIExecutor.POST.execute(webParam);
            } else if (webParam.httpType == HttpType.PUT) {
                APIExecutor.PUT.execute(webParam);
            } else if (webParam.httpType == HttpType.DELETE) {
                APIExecutor.DELETE.execute(webParam);
            } else {
                APIExecutor.MULTIPART.execute(webParam);
            }
        }
    }
}
