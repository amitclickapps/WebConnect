package webconnect.com.webconnect

import android.app.Activity
import android.content.Context

import java.io.File

/**
 * Created by amit on 23/9/17.
 */

class Builder {

    private var webParam: WebParam? = null

    constructor(context: Activity, url: String) {
        webParam = WebParam()
        webParam?.activityContext = context
        webParam?.context = context
        webParam?.url = url
    }

    constructor(context: Context, url: String) {
        webParam = WebParam()
        webParam?.context = context
        webParam?.url = url
    }

    fun get(): BuilderRequest.GetRequestBuilder {
        webParam?.httpType = WebParam.HttpType.GET
        return BuilderRequest.GetRequestBuilder(webParam!!)
    }

    fun head(): BuilderRequest.HeadRequestBuilder {
        webParam?.httpType = WebParam.HttpType.HEAD
        return BuilderRequest.HeadRequestBuilder(webParam!!)
    }

    fun options(): BuilderRequest.OptionsRequestBuilder {
        webParam?.httpType = WebParam.HttpType.OPTIONS
        return BuilderRequest.OptionsRequestBuilder(webParam!!)
    }

    fun post(): BuilderRequest.PostRequestBuilder {
        webParam?.httpType = WebParam.HttpType.POST
        return BuilderRequest.PostRequestBuilder(webParam!!)
    }

    fun put(): BuilderRequest.PutRequestBuilder {
        webParam?.httpType = WebParam.HttpType.PUT
        return BuilderRequest.PutRequestBuilder(webParam!!)
    }

    fun delete(): BuilderRequest.DeleteRequestBuilder {
        webParam?.httpType = WebParam.HttpType.DELETE
        return BuilderRequest.DeleteRequestBuilder(webParam!!)
    }

    fun patch(): BuilderRequest.PatchRequestBuilder {
        webParam?.httpType = WebParam.HttpType.PATCH
        return BuilderRequest.PatchRequestBuilder(webParam!!)
    }

    fun download(file: File): BuilderRequest.DownloadBuilder {
        webParam?.httpType = WebParam.HttpType.GET
        webParam?.file = file
        return BuilderRequest.DownloadBuilder(webParam!!)
    }

    fun multipart(): BuilderRequest.MultiPartBuilder {
        webParam?.httpType = WebParam.HttpType.MULTIPART
        return BuilderRequest.MultiPartBuilder(webParam!!)
    }

}
