package webconnect.com.webconnect

import android.app.Dialog
import android.text.TextUtils
import com.rx2androidnetworking.Rx2ANRequest
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import webconnect.com.webconnect.di.IProperties
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by clickapps on 27/12/17.
 */
open class Request {
    var param: WebParam?
        private set

    constructor(builder: GetRequestBuilder) {
        param = builder.param
    }

    constructor(builder: PostRequestBuilder) {
        param = builder.param
    }

    constructor(builder: DownloadBuilder) {
        param = builder.param
    }

    constructor(builder: MultiPartBuilder) {
        param = builder.param
    }

    fun execute(): Observable<*> {
        when (param?.httpType) {
            WebParam.HttpType.GET -> return GetRequestBuilder(param!!).execute()
            WebParam.HttpType.HEAD -> return GetRequestBuilder(param!!).execute()
            WebParam.HttpType.OPTIONS -> return GetRequestBuilder(param!!).execute()
            WebParam.HttpType.POST -> return PostRequestBuilder(param!!).execute();
            WebParam.HttpType.PUT -> return PostRequestBuilder(param!!).execute();
            WebParam.HttpType.DELETE -> return PostRequestBuilder(param!!).execute();
            WebParam.HttpType.MULTIPART -> return PostRequestBuilder(param!!).execute();
            else -> {
                return GetRequestBuilder(param!!).execute()
            }
        }
    }

    open class GetRequestBuilder(internal val param: WebParam) : IProperties {
        private var okHttpClient: OkHttpClient? = null

        override fun baseUrl(url: String): GetRequestBuilder {
            param.url = url
            return this
        }

        override fun headerParam(headerParam: Map<String, String>): GetRequestBuilder {
            param.headerParam = headerParam
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback): GetRequestBuilder {
            param.callback = callback
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback, success: Class<*>, error: Class<*>): GetRequestBuilder {
            param.callback = callback
            param.model = success
            param.error = error
            return this
        }

        override fun taskId(taskId: Int): GetRequestBuilder {
            param.taskId = taskId
            return this
        }

        override fun timeOut(connectTimeOut: Long, readTimeOut: Long): GetRequestBuilder {
            param.connectTimeOut = connectTimeOut
            param.readTimeOut = readTimeOut
            okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(param.readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .build()
            return this
        }

        override fun progressDialog(dialog: Dialog): GetRequestBuilder {
            param.dialog = dialog
            return this
        }

        override fun cache(isCache: Boolean): GetRequestBuilder {
            param.isCacheEnabled = isCache
            return this
        }

        fun queryParam(requestParam: Map<String, String>): GetRequestBuilder {
            param.requestParam = requestParam
            return this
        }

        open fun build(): Request {
            return Request(this)
        }

        override fun connect() {
            execute().subscribe(Callback.GetRequestCallback(param))
            //            IAPIService request = RetrofitManager.get().createService(param);
            //            if (param.httpType == WebParam.HttpType.GET) {
            //                request.get(param.url, (Map<String, Object>) param.requestParam)
            //                        .subscribeOn(Schedulers.io())
            //                        .observeOn(AndroidSchedulers.mainThread())
            //                        .subscribe(new Callback.RequestCallback<>(param));
            //            } else if (param.httpType == WebParam.HttpType.HEAD) {
            //                request.head(param.url, (Map<String, Object>) param.requestParam)
            //                        .subscribeOn(Schedulers.io())
            //                        .observeOn(AndroidSchedulers.mainThread())
            //                        .subscribe(new Callback.RequestCallback<>(param));
            //            } else if (param.httpType == WebParam.HttpType.OPTIONS) {
            //                request.options(param.url, (Map<String, Object>) param.requestParam)
            //                        .subscribeOn(Schedulers.io())
            //                        .observeOn(AndroidSchedulers.mainThread())
            //                        .subscribe(new Callback.RequestCallback<>(param));
            //            }
        }


        fun execute(): Observable<*> {
            var baseUrl = param.baseUrl
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.baseUrl
            }
            var getBuilder: Rx2ANRequest.GetRequestBuilder
            when (param.httpType) {
                WebParam.HttpType.GET -> getBuilder = Rx2AndroidNetworking.get(baseUrl + param.url)
                WebParam.HttpType.HEAD -> getBuilder = Rx2AndroidNetworking.head(baseUrl + param.url)
                WebParam.HttpType.OPTIONS -> getBuilder = Rx2AndroidNetworking.options(baseUrl + param.url)
                else -> getBuilder = Rx2AndroidNetworking.get(baseUrl + param.url)
            }
            getBuilder.addQueryParameter(param.requestParam)
                    .addHeaders(param.headerParam)
            if (okHttpClient != null) {
                getBuilder.setOkHttpClient(okHttpClient)
            }
            if (param.isCacheEnabled) {
                getBuilder.setMaxAgeCacheControl(Int.MAX_VALUE, TimeUnit.SECONDS)
                getBuilder.setMaxStaleCacheControl(1, TimeUnit.SECONDS)
                getBuilder.responseOnlyIfCached
            } else getBuilder.responseOnlyFromNetwork
            return getBuilder.setTag(param.taskId)
                    .build()
                    .getObjectObservable(param.model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    open class PostRequestBuilder(internal val param: WebParam) : IProperties {
        private var okHttpClient: OkHttpClient? = null

        override fun baseUrl(url: String): PostRequestBuilder {
            param.url = url
            return this
        }

        override fun headerParam(headerParam: Map<String, String>): PostRequestBuilder {
            param.headerParam = headerParam
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback): PostRequestBuilder {
            param.callback = callback
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback, success: Class<*>, error: Class<*>): PostRequestBuilder {
            param.callback = callback
            param.model = success
            param.error = error
            return this
        }

        override fun taskId(taskId: Int): PostRequestBuilder {
            param.taskId = taskId
            return this
        }

        override fun timeOut(connectTimeOut: Long, readTimeOut: Long): PostRequestBuilder {
            param.connectTimeOut = connectTimeOut
            param.readTimeOut = readTimeOut
            okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(param.readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .build()
            return this
        }

        override fun progressDialog(dialog: Dialog): PostRequestBuilder {
            param.dialog = dialog
            return this
        }

        override fun cache(isCache: Boolean): PostRequestBuilder {
            param.isCacheEnabled = isCache
            return this
        }

        fun bodyParam(requestParam: Map<String, Any>): PostRequestBuilder {
            param.requestParam = requestParam
            return this
        }

        fun addFile(file: File): PostRequestBuilder {
            param.file = file
            return this
        }

        open fun build(): Request {
            return Request(this)
        }

        override fun connect() {
            execute().subscribe(Callback.PostRequestCallback(param))
        }

        fun execute(): Observable<*> {
            var baseUrl = param.baseUrl
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.baseUrl
            }
            var postBuilder: Rx2ANRequest.PostRequestBuilder
            when (param.httpType) {
                WebParam.HttpType.POST -> postBuilder = Rx2AndroidNetworking.post(baseUrl + param.url)
                WebParam.HttpType.PUT -> postBuilder = Rx2AndroidNetworking.put(baseUrl + param.url)
                WebParam.HttpType.DELETE -> postBuilder = Rx2AndroidNetworking.delete(baseUrl + param.url)
                WebParam.HttpType.PATCH -> postBuilder = Rx2AndroidNetworking.patch(baseUrl + param.url)
                else -> postBuilder = Rx2AndroidNetworking.post(baseUrl + param.url)
            }
            postBuilder.addBodyParameter(param.requestParam)
                    .addHeaders(param.headerParam)
            if (param.file != null) {
                postBuilder.addFileBody(param.file)
            }
            if (okHttpClient != null) {
                postBuilder.setOkHttpClient(okHttpClient)
            }
            if (param.isCacheEnabled) {
                postBuilder.setMaxAgeCacheControl(Int.MAX_VALUE, TimeUnit.SECONDS)
                postBuilder.setMaxStaleCacheControl(1, TimeUnit.SECONDS)
                postBuilder.responseOnlyIfCached
            } else postBuilder.responseOnlyFromNetwork
            return postBuilder.setTag(param.taskId)
                    .build()
                    .getObjectObservable(param.model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }

    open class DownloadBuilder(internal val param: WebParam) : IProperties {
        private var okHttpClient: OkHttpClient? = null

        override fun baseUrl(url: String): DownloadBuilder {
            param.url = url
            return this
        }

        override fun headerParam(headerParam: Map<String, String>): DownloadBuilder {
            param.headerParam = headerParam
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback): DownloadBuilder {
            param.callback = callback
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback, success: Class<*>, error: Class<*>): DownloadBuilder {
            param.callback = callback
            param.model = success
            param.error = error
            return this
        }

        override fun taskId(taskId: Int): DownloadBuilder {
            param.taskId = taskId
            return this
        }

        override fun timeOut(connectTimeOut: Long, readTimeOut: Long): DownloadBuilder {
            param.connectTimeOut = connectTimeOut
            param.readTimeOut = readTimeOut
            okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(param.readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(param.connectTimeOut, TimeUnit.SECONDS)
                    .build()
            return this
        }

        override fun progressDialog(dialog: Dialog): DownloadBuilder {
            param.dialog = dialog
            return this
        }

        override fun cache(isCache: Boolean): DownloadBuilder {
            param.isCacheEnabled = isCache
            return this
        }

        fun queryParam(requestParam: Map<String, String>): DownloadBuilder {
            param.requestParam = requestParam
            return this
        }

        fun progressListener(callback: WebHandler.ProgressListener): DownloadBuilder {
            param.progressListener = callback
            return this
        }

        fun file(file: File): DownloadBuilder {
            param.file = file
            return this
        }

        override fun connect() {
            var baseUrl = param.baseUrl
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.baseUrl
            }
            val downloadBuilder = Rx2AndroidNetworking.download(baseUrl + param.url, param.file?.path, param.file?.name)
            downloadBuilder.addQueryParameter(param.requestParam)
                    .setTag(param.taskId)
                    .addHeaders(param.headerParam)
            if (okHttpClient != null) {
                downloadBuilder.setOkHttpClient(okHttpClient)
            }
            val okHttpRequest = downloadBuilder.build()
            okHttpRequest.downloadProgressListener = Callback.ProgressCallback(param)
            okHttpRequest.startDownload(Callback.DownloadRequestCallback(param))
        }
    }

    open class MultiPartBuilder(internal val param: WebParam) : IProperties {

        override fun baseUrl(url: String): MultiPartBuilder {
            param.url = url
            return this
        }

        override fun headerParam(headerParam: Map<String, String>): MultiPartBuilder {
            param.headerParam = headerParam
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback): MultiPartBuilder {
            param.callback = callback
            return this
        }

        override fun callback(callback: WebHandler.OnWebCallback, success: Class<*>, error: Class<*>): MultiPartBuilder {
            param.callback = callback
            param.model = success
            param.error = error
            return this
        }

        override fun taskId(taskId: Int): MultiPartBuilder {
            param.taskId = taskId
            return this
        }

        override fun timeOut(connectTimeOut: Long, readTimeOut: Long): MultiPartBuilder {
            param.connectTimeOut = connectTimeOut
            param.readTimeOut = readTimeOut
            return this
        }

        override fun progressDialog(dialog: Dialog): MultiPartBuilder {
            param.dialog = dialog
            return this
        }

        override fun cache(isCache: Boolean): MultiPartBuilder {
            param.isCacheEnabled = isCache
            return this
        }

        fun multipartParam(multipartParam: Map<String, String>): MultiPartBuilder {
            param.multipartParam = multipartParam
            return this
        }

        fun multipartParamFile(multipartFile: Map<String, File>): MultiPartBuilder {
            param.multipartParamFile = multipartFile
            return this
        }

        fun progressListener(callback: WebHandler.ProgressListener): MultiPartBuilder {
            param.progressListener = callback
            return this
        }

        open fun build(): Request {
            return Request(this)
        }

        override fun connect() {
            execute().subscribe(Callback.UploadRequestCallback(param))
        }

        fun execute(): Observable<*> {
            var baseUrl = param.baseUrl
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = ApiConfiguration.baseUrl
            }
            val multipartBuilder = Rx2AndroidNetworking.upload(baseUrl + param.url)
            return multipartBuilder.addHeaders(param.headerParam)
                    .addMultipartFile(param.multipartParamFile)
                    .addMultipartParameter(param.multipartParam)
                    .setTag(param.taskId)
                    .build()
                    .getObjectObservable(param.model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }
}
