package webconnect.com.webconnect

import android.app.Activity
import android.content.Context


/**
 * The type Web connect.
 */
object WebConnect {
    @Volatile private var sWebConnect: WebConnect? = null

    /**
     * Get web connect.
     *
     * @return the web connect
     */
    fun get(): WebConnect? {
        if (sWebConnect == null) {
            synchronized(WebConnect::class.java) {
                if (sWebConnect == null) {
                    sWebConnect = WebConnect
                }
            }
        }
        return sWebConnect
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param url     the url
     * @return the builder
     */
    fun with(context: Activity, url: String): Builder = Builder(context, url)

    /**
     * With builder.
     *
     * @param context the context
     * @param url     the url
     * @return the builder
     */
    fun with(context: Context, url: String): Builder = Builder(context, url)
}
