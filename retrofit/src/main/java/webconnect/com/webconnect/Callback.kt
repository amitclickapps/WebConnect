package webconnect.com.webconnect

/**
 * Created by amit on 10/8/17.
 */

import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.DownloadListener
import com.androidnetworking.interfaces.DownloadProgressListener
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.File
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException
import java.util.concurrent.TimeoutException

/**
 * The type Call back.
 *
 * @param <T> the type parameter
</T> */
class Callback<T> {

    internal class GetRequestCallback(private val param: WebParam) : Observer<Any> {

        override fun onSubscribe(@io.reactivex.annotations.NonNull d: Disposable) {
            if (param.dialog !=null && !param.dialog?.isShowing!!) {
                param.dialog?.show()
            }
        }

        override fun onNext(@io.reactivex.annotations.NonNull response: Any) {
            param.callback?.onSuccess(response, param.taskId, null)
        }

        override fun onError(@io.reactivex.annotations.NonNull e: Throwable) {
            param.callback?.onError(e, getError(param, e), param.taskId)
        }

        override fun onComplete() {
            if (param.dialog !=null && param.dialog?.isShowing!!) {
                param.dialog?.dismiss()
            }
        }
    }

    internal open class PostRequestCallback(private val param: WebParam) : Observer<Any> {

        override fun onSubscribe(@io.reactivex.annotations.NonNull d: Disposable) {
            if (param.dialog !=null && !param.dialog?.isShowing!!) {
                param.dialog?.show()
            }
        }

        override fun onNext(@io.reactivex.annotations.NonNull response: Any) {
            param.callback?.onSuccess(response, param.taskId, null)
        }

        override fun onError(@io.reactivex.annotations.NonNull e: Throwable) {
            param.callback?.onError(e, getError(param, e), param.taskId)
        }

        override fun onComplete() {
            if (param.dialog !=null && param.dialog?.isShowing!!) {
                param.dialog?.dismiss()
            }
        }
    }


    internal class DownloadRequestCallback(private val param: WebParam) : DownloadListener {

        override fun onDownloadComplete() {
            param.callback?.onSuccess<File>(this.param.file, this.param.taskId, null)
        }

        override fun onError(anError: ANError) {
            param.callback?.onError(anError.cause, getError(param, anError.cause!!), param.taskId)
        }
    }


    internal class UploadRequestCallback(param: WebParam) : PostRequestCallback(param)

    internal class ProgressCallback(private val param: WebParam) : DownloadProgressListener {

        override fun onProgress(bytesDownloaded: Long, totalBytes: Long) {
            param.progressListener?.update(bytesDownloaded, totalBytes, true)
        }
    }

    companion object {

        private fun getError(param: WebParam, t: Throwable): String {
            val errors: String
            if (t.javaClass.name.contains(UnknownHostException::class.java.name)) {
                errors = param.context?.getString(R.string.error_internet_connection).toString()
            } else if (t.javaClass.name.contains(TimeoutException::class.java.name)
                    || t.javaClass.name.contains(SocketTimeoutException::class.java.name)
                    || t.javaClass.name.contains(ConnectException::class.java.name)) {
                errors = param.context?.getString(R.string.error_server_connection).toString()
            } else if (t.javaClass.name.contains(CertificateException::class.java.name)) {
                errors = param.context?.getString(R.string.error_certificate_exception).toString()
            } else {
                errors = t.toString()
            }
            return errors
        }
    }

}
