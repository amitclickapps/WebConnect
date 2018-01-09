package webconnect.com.webconnect

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by amit on 10/8/17.
 */

object ApiConfiguration {
    var baseUrl = ""
        internal set
    var connectTimeOut = (10 * 1000).toLong()
        internal set
    var readTimeOut = (20 * 1000).toLong()
        internal set
    val gson = GsonBuilder()
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .setLenient()
            .create()
    var isDebug = true
        internal set
    private val okHttpClient = OkHttpClient()
    private val dispatcher = Dispatcher()

    class Builder(private val context: Application) {
        private var baseUrl = ""
        private var connectTimeOut = (10 * 1000).toLong()
        private var readTimeOut = (20 * 1000).toLong()
        private var isDebug = true

        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun timeOut(connectTimeOut: Long, readTimeOut: Long): Builder {
            this.connectTimeOut = connectTimeOut
            this.readTimeOut = readTimeOut
            return this
        }

        fun debug(isDebug: Boolean): Builder {
            this.isDebug = isDebug
            return this
        }

        fun config() {
            ApiConfiguration.baseUrl = baseUrl
            ApiConfiguration.connectTimeOut = connectTimeOut
            ApiConfiguration.readTimeOut = readTimeOut
            ApiConfiguration.isDebug = isDebug
            dispatcher.maxRequestsPerHost = 2
            dispatcher.maxRequests = 10
            okHttpClient
                    .newBuilder()
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(connectTimeOut, TimeUnit.SECONDS)
                    .dispatcher(dispatcher)
                    .build()
            AndroidNetworking.initialize(context, okHttpClient)
            AndroidNetworking.enableLogging(if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }
    }
}
