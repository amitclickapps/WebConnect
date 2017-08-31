package webconnect.com.webconnect;

/**
 * Created by amit on 10/8/17.
 */

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
            Object object;
            if (webParam.getCallback() == null) {
                return;
            }

            if (response.isSuccessful()) {
                res = response.body().toString();
                if (webParam.getModel() != null
                        && !TextUtils.isEmpty(res)) {
                    object = Configuration.getGson().fromJson(res, webParam.getModel());
                } else if (!TextUtils.isEmpty(res)) {
                    object = Configuration.getGson().fromJson(res, Object.class);
                } else {
                    object = res;
                }
                webParam.getCallback().onSuccess(object, webParam.getTaskId(), response);
            } else {
                res = response.errorBody().string();
                if (webParam.getError() != null
                        && !TextUtils.isEmpty(res)) {
                    object = Configuration.getGson().fromJson(res, webParam.getError());
                } else if (!TextUtils.isEmpty(res)) {
                    object = Configuration.getGson().fromJson(res, Object.class);
                } else {
                    object = res;
                }
                webParam.getCallback().onError(object, res, webParam.getTaskId(), response);

            }
        } catch (Exception e) {
            e.printStackTrace();
            webParam.getCallback().onError(e, res, webParam.getTaskId(), response);
        }
    }

    @Override
    public void onError(@NonNull Throwable t) {
        try {
            if (webParam.getCallback() != null
                    && webParam.getContext() != null) {
                String errors;
                if (t.getClass().getName().contains(UnknownHostException.class.getName())) {
                    errors = webParam.getContext().getString(R.string.error_internet_connection);
                } else if (t.getClass().getName().contains(TimeoutException.class.getName())
                        || t.getClass().getName().contains(SocketTimeoutException.class.getName())
                        || t.getClass().getName().contains(ConnectException.class.getName())) {
                    errors = webParam.getContext().getString(R.string.error_server_connection);
                } else if (t.getClass().getName().contains(CertificateException.class.getName())) {
                    errors = webParam.getContext().getString(R.string.error_certificate_exception);
                } else {
                    errors = t.toString();
                }
                webParam.getCallback().onError(errors, errors, webParam.getTaskId(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) {
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {

    }
}
