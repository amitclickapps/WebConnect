package webconnect.com.webconnect.di;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.Map;

import webconnect.com.webconnect.WebHandler;
import webconnect.com.webconnect.WebParam;

/**
 * Created by amit on 23/9/17.
 */

public interface IProperties {

    IProperties baseUrl(@NonNull String url);

    IProperties httpType(@NonNull WebParam.HttpType httpType);

    IProperties requestParam(@NonNull Map<String, ?> requestParam);

    IProperties multipartParam(@NonNull Map<String, ?> multipartParam);

    IProperties headerParam(@NonNull Map<String, String> headerParam);

    IProperties callback(@NonNull WebHandler.OnWebCallback callback);

    IProperties callback(@NonNull WebHandler.OnWebCallback callback,
                         @NonNull Class<?> success, @NonNull Class<?> error);

    IProperties successModel(@NonNull Class<?> success);

    IProperties errorModel(@NonNull Class<?> error);

    IProperties taskId(int taskId);

    IProperties timeOut(long connectTimeOut, long readTimeOut);

    IProperties downloadFile(File file);

    @Deprecated
    WebParam build();

    /**
     * To use this have to set these values
     * <p>
     * httpType(HttpType) <br/>
     * pathSegment(String) <br/>
     * pathSegment(String)<br/>
     * <p>
     *
     * @see #httpType(WebParam.HttpType)
     */
    void connect();
}
