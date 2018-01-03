package webconnect.com.webconnect;

import android.support.annotation.Nullable;


/**
 * The type Web handler.
 */
public class WebHandler {

    /**
     * The interface On web callback.
     */
    public interface OnWebCallback {
        /**
         * On success.
         *
         * @param <T>      the type parameter
         * @param object   the object
         * @param response the response
         * @param taskId   the task id
         * @param response the status code
         */
        <T> void onSuccess(@Nullable T object, int taskId, retrofit2.Response response);

        /**
         * On error.
         *
         * @param <T>    the type parameter
         * @param object the object
         * @param error  the error
         * @param taskId the task id
         */
        <T> void onError(@Nullable T object, String error, int taskId);
    }

    public interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

    public interface AnalyticsListener {
        void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache);
    }
}
