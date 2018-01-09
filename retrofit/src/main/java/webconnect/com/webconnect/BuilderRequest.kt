package webconnect.com.webconnect

/**
 * Created by clickapps on 27/12/17.
 */

class BuilderRequest : Request {

    constructor(builder: GetRequestBuilder) : super(builder) {}

    constructor(builder: PostRequestBuilder) : super(builder) {}

    constructor(builder: DownloadBuilder) : super(builder) {}

    constructor(builder: MultiPartBuilder) : super(builder) {}

    open class GetRequestBuilder(param: WebParam) : Request.GetRequestBuilder(param) {

        override fun build(): BuilderRequest {
            return BuilderRequest(this)
        }
    }

    class HeadRequestBuilder(param: WebParam) : GetRequestBuilder(param)

    class OptionsRequestBuilder(param: WebParam) : GetRequestBuilder(param)

    open class PostRequestBuilder(param: WebParam) : Request.PostRequestBuilder(param) {

        override fun build(): BuilderRequest {
            return BuilderRequest(this)
        }
    }

    class PutRequestBuilder(param: WebParam) : PostRequestBuilder(param)

    class DeleteRequestBuilder(param: WebParam) : PostRequestBuilder(param)

    class PatchRequestBuilder(param: WebParam) : PostRequestBuilder(param)

    class DynamicRequestBuilder(param: WebParam) : PostRequestBuilder(param)

    class DownloadBuilder(param: WebParam) : Request.DownloadBuilder(param) {

    }

    class MultiPartBuilder(param: WebParam) : Request.MultiPartBuilder(param) {

        override fun build(): BuilderRequest {
            return BuilderRequest(this)
        }
    }


}
