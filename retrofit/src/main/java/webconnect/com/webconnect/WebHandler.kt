package webconnect.com.webconnect


/**
 * The type Web handler.
 */
class WebHandler {

    /**
     * The interface On web callback.
     */
    interface OnWebCallback {
        /**
         * On success.
         *
         * @param <T>      the type parameter
         * @param object   the object
         * @param response the response
         * @param taskId   the task id
         * @param response the status code
        </T> */
        fun <T> onSuccess(`object`: T?, taskId: Int, response: retrofit2.Response<*>?)

        /**
         * On error.
         *
         * @param <T>    the type parameter
         * @param object the object
         * @param error  the error
         * @param taskId the task id
        </T> */
        fun <T> onError(`object`: T?, error: String, taskId: Int)
    }

    interface ProgressListener {
        fun update(bytesRead: Long, contentLength: Long, done: Boolean)
    }

    interface AnalyticsListener {
        fun onReceived(timeTakenInMillis: Long, bytesSent: Long, bytesReceived: Long, isFromCache: Boolean)
    }
}
