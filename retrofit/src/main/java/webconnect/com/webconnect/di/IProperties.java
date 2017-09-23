package webconnect.com.webconnect.di;

import android.support.annotation.NonNull;

import java.util.Map;

import webconnect.com.webconnect.WebHandler;
import webconnect.com.webconnect.WebParam;

/**
 * Created by amit on 23/9/17.
 */

public interface IProperties {

    public IProperties baseUrl(@NonNull String url);

    public IProperties pathSegment(@NonNull String pathSegment);

    public IProperties pathSegmentParam(@NonNull String pathSegmentParam);

    public IProperties httpType(@NonNull WebParam.HttpType httpType);

    public IProperties requestParam(@NonNull Map<String, ?> requestParam);

    public IProperties multipartParam(@NonNull Map<String, ?> multipartParam);

    public IProperties headerParam(@NonNull Map<String, String> headerParam);

    public IProperties callback(@NonNull WebHandler.OnWebCallback callback);

    public IProperties callback(@NonNull WebHandler.OnWebCallback callback,
                                @NonNull Class<?> success, @NonNull Class<?> error);

    public IProperties successModel(@NonNull Class<?> success);

    public IProperties errorModel(@NonNull Class<?> error);

    public IProperties taskId(int taskId);

    @Deprecated
    public WebParam build();

    /**
     * To use this have to set these values
     * <p>
     * httpType(HttpType) <br/>
     * pathSegment(String) <br/>
     * pathSegment(String)<br/>
     * <p>
     *
     * @see #httpType(WebParam.HttpType)
     * @see #pathSegment(String)
     * @see #pathSegmentParam(String)
     */
    public void connect();
}
