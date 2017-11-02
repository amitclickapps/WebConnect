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
         * @param <T>      the type parameter
         * @param object   the object
         * @param error    the error
         * @param taskId   the task id
         */
        <T> void onError(@Nullable T object, String error, int taskId);
    }

    public interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

    /**
     * The type Web callback.
     */
    public abstract class WebCallback implements OnWebCallback {

        @Override
        public <T> void onSuccess(@Nullable T object, int taskId, retrofit2.Response response) {

        }

        @Override
        public <T> void onError(@Nullable T object, String error, int taskId) {

        }
    }
}
