package webconnect.com.webconnect;

/**
 * Created by amit on 10/8/17.
 */

import android.text.TextUtils;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * The type Call back.
 *
 * @param <T> the type parameter
 */
public class Callback<T> implements Observer<Response<T>> {
    private WebParam webParam;

    /**
     * Instantiates a new Call back.
     *
     * @param webParam the web param
     */
    public Callback(WebParam webParam) {
        this.webParam = webParam;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull retrofit2.Response<T> response) {
        String res = "";
        try {
            if (webParam.dialog != null &&
                    webParam.dialog.isShowing()) {
                webParam.dialog.dismiss();
            }
            Object object;
            if (webParam.callback == null) {
                return;
            }

            if (response.isSuccessful()) {
                if (response.body() instanceof String) {
                    res = response.body().toString();
                }
                if (!webParam.isFile) {
                    if (webParam.model != null
                            && !TextUtils.isEmpty(res)) {
                        object = ApiConfiguration.getGson().fromJson(res, webParam.model);
                    } else if (!TextUtils.isEmpty(res)) {
                        object = ApiConfiguration.getGson().fromJson(res, Object.class);
                    } else {
                        object = res;
                    }
                } else {
                    ResponseBody body = (ResponseBody) response.body();
                    object = webParam.file;
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(webParam.file);
                        IOUtils.copy(body.byteStream(), out);
                    } finally {
                        IOUtils.closeQuietly(out);
                    }
                }
                webParam.callback.onSuccess(object, webParam.taskId, response);
            } else {
                res = response.errorBody().string();
                if (webParam.error != null
                        && !TextUtils.isEmpty(res)) {
                    object = ApiConfiguration.getGson().fromJson(res, webParam.error);
                } else if (!TextUtils.isEmpty(res)) {
                    object = ApiConfiguration.getGson().fromJson(res, Object.class);
                } else {
                    object = res;
                }
                webParam.callback.onError(object, res, webParam.taskId);

            }
        } catch (Exception e) {
            if (ApiConfiguration.isDebug()) {
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
            webParam.callback.onError(e, res, webParam.taskId);
        }
    }

    @Override
    public void onError(@NonNull Throwable t) {
        try {
            if (webParam.dialog != null &&
                    webParam.dialog.isShowing()) {
                webParam.dialog.dismiss();
            }
            if (webParam.callback != null
                    && webParam.context != null) {
                String errors;
                if (t.getClass().getName().contains(UnknownHostException.class.getName())) {
                    errors = webParam.context.getString(R.string.error_internet_connection);
                } else if (t.getClass().getName().contains(TimeoutException.class.getName())
                        || t.getClass().getName().contains(SocketTimeoutException.class.getName())
                        || t.getClass().getName().contains(ConnectException.class.getName())) {
                    errors = webParam.context.getString(R.string.error_server_connection);
                } else if (t.getClass().getName().contains(CertificateException.class.getName())) {
                    errors = webParam.context.getString(R.string.error_certificate_exception);
                } else {
                    errors = t.toString();
                }
                webParam.callback.onError(errors, errors, webParam.taskId);
            }
        } catch (Exception e) {
            if (ApiConfiguration.isDebug()) {
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
            webParam.callback.onError(e.getMessage(), e.getMessage(), webParam.taskId);
        }
    }

    @Override
    public void onComplete() {

    }
}
