package webconnect.com.webconnect

import android.app.Activity
import android.app.Dialog
import android.content.Context
import java.io.File
import java.io.Serializable
import java.util.*


/**
 * The type Web param.
 */
class WebParam : Serializable {
    /**
     * The Activity context.
     */
    var activityContext: Activity? = null
        internal set
    /**
     * The Context.
     */
    var context: Context? = null
        internal set
    /**
     * The Url.
     */
    var url: String? = null
        internal set
    /**
     * The Base url.
     */
    var baseUrl: String? = null
        internal set
    /**
     * The Http type.
     */
    var httpType = HttpType.GET
        internal set
    /**
     * The Request param.
     */
    var requestParam: Map<String, Any> = LinkedHashMap<String, Any>()
        internal set

    /**
     * The Multipart param.
     */
    var multipartParam: Map<String, String> = LinkedHashMap()
        internal set
    var multipartParamFile: Map<String, File> = LinkedHashMap()
        internal set
    /**
     * The Header param.
     */
    var headerParam: Map<String, String> = LinkedHashMap()
        internal set
    /**
     * The Callback.
     */
    var callback: WebHandler.OnWebCallback? = null
        internal set
    /**
     * Progress during download
     */
    var progressListener: WebHandler.ProgressListener? = null
        internal set

    var analyticsListener: WebHandler.AnalyticsListener? = null
        internal set
    /**
     * Progress Dialog
     */
    var dialog: Dialog? = null
        internal set
    /**
     * The Model.
     */
    var model: Class<*> = Object::class.java
        internal set
    /**
     * The Error.
     */
    var error: Class<*> = Object::class.java
        internal set
    /**
     * The Task id.
     */
    var taskId: Int = 0
        internal set

    var connectTimeOut: Long = 0
        internal set
    var readTimeOut: Long = 0
        internal set
    /**
     * Download File anyType
     */
    var isCacheEnabled = false
        internal set
    var file: File? = null
        internal set

    /**
     * The enum Http type.
     */
    enum class HttpType {
        /**
         * Get http type.
         */
        GET,
        /**
         * Post http type.
         */
        POST,
        /**
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
}
