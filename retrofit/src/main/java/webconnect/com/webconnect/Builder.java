package webconnect.com.webconnect;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.Map;

import webconnect.com.webconnect.di.IProperties;

/**
 * Created by amit on 23/9/17.
 */

public class Builder implements IProperties {

    private WebParam webParam;

    public Builder(@NonNull Activity context, @NonNull String url) {
        webParam = new WebParam();
        webParam.activityContext = context;
        webParam.context = context;
        webParam.url = url;
    }

    public Builder(@NonNull Context context, @NonNull String url) {
        webParam = new WebParam();
        webParam.context = context;
        webParam.url = url;
    }

    @Override
    public Builder baseUrl(@NonNull String url) {
        webParam.baseUrl = url;
        return this;
    }

    @Override
    public Builder httpType(@NonNull WebParam.HttpType httpType) {
        webParam.httpType = httpType;
        return this;
    }

    @Override
    public Builder requestParam(@NonNull Map<String, ?> requestParam) {
        webParam.requestParam = requestParam;
        return this;
    }

    @Override
    public Builder multipartParam(@NonNull Map<String, ?> multipartParam) {
        webParam.multipartParam = multipartParam;
        return this;
    }

    @Override
    public Builder headerParam(@NonNull Map<String, String> headerParam) {
        webParam.headerParam = headerParam;
        return this;
    }

    @Override
    public Builder callback(@NonNull WebHandler.OnWebCallback callback) {
        webParam.callback = callback;
        return this;
    }

    @Override
    public Builder callback(@NonNull WebHandler.OnWebCallback callback,
                            @NonNull Class<?> success, @NonNull Class<?> error) {
        webParam.callback = callback;
        webParam.model = success;
        webParam.error = error;
        return this;
    }

    @Override
    public Builder successModel(@NonNull Class<?> success) {
        webParam.model = success;
        return this;
    }

    @Override
    public Builder errorModel(@NonNull Class<?> error) {
        webParam.error = error;
        return this;
    }

    @Override
    public Builder taskId(int taskId) {
        webParam.taskId = taskId;
        return this;
    }

    @Override
    public Builder timeOut(long connectTimeOut, long readTimeOut) {
        webParam.connectTimeOut = connectTimeOut;
        webParam.readTimeOut = readTimeOut;
        return this;
    }

    @Override
    public Builder downloadFile(File file) {
        webParam.file = file;
        webParam.isFile = true;
        return this;
    }

    @Override
    public Builder progressListener(@NonNull WebHandler.ProgressListener callback) {
        webParam.progressListener = callback;
        return this;
    }

    @Override
    public Builder progressDialog(Dialog dialog) {
        webParam.dialog = dialog;
        return this;
    }

    @Override
    public Builder cache(boolean isCache) {
        webParam.isCacheEnabled = isCache;
        return this;
    }


    @Override
    @Deprecated
    public WebParam build() {
        return webParam;
    }

    @Override
    public void connect() {
        if (!webParam.isFile) {
            if (webParam.httpType == WebParam.HttpType.GET) {
                APIExecutor.GET.execute(webParam);
            } else if (webParam.httpType == WebParam.HttpType.POST) {
                APIExecutor.POST.execute(webParam);
            } else if (webParam.httpType == WebParam.HttpType.PUT) {
                APIExecutor.PUT.execute(webParam);
            } else if (webParam.httpType == WebParam.HttpType.PATCH) {
                APIExecutor.PATCH.execute(webParam);
            } else if (webParam.httpType == WebParam.HttpType.DELETE) {
                APIExecutor.DELETE.execute(webParam);
            } else if (webParam.httpType == WebParam.HttpType.MULTIPART) {
                APIExecutor.MULTIPART.execute(webParam);
            }
        } else {
            APIExecutor.GET_FILE.execute(webParam);
        }
    }
}
