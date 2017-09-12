package webconnect.com.webconnect;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;


/**
 * The type Web connect.
 */
public class WebConnect {
    private static volatile WebConnect sWebConnect;

    private WebConnect() {

    }

    /**
     * Get web connect.
     *
     * @return the web connect
     */
    public static WebConnect get() {
        if (sWebConnect == null) {
            synchronized (WebConnect.class) {
                if (sWebConnect == null) {
                    sWebConnect = new WebConnect();
                }
            }
        }
        return sWebConnect;
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param pathSegment     the pathSegment
     * @return the builder
     */
    public static WebParam.Builder with(@NonNull Activity context, @NonNull String pathSegment) {
        return new WebParam.Builder(context, pathSegment);

    }

    /**
     * With builder.
     *
     * @param context the context
     * @param pathSegment     the pathSegment
     * @return the builder
     */
    public static WebParam.Builder with(@NonNull Context context, @NonNull String pathSegment) {
        return new WebParam.Builder(context, pathSegment);
    }

    /*
     * Connect
     */
    @Deprecated
    public static <T> T connect(@NonNull Class<T> cls, @NonNull WebParam webParam) {
        return new RetrofitManager().createService(cls, webParam);
    }
}
