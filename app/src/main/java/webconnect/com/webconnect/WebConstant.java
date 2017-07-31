package webconnect.com.webconnect;


/**
 * The type Web constant.
 */
public class WebConstant {
    /*Base urls*/
    private static String mBaseUrl = "";
    /*Api version*/
    private static String mApiVersion = "";

    /**
     * <p>Functionality to set the base url.</p>
     * <p>the format should be such as http://api.dev.arbab.clicksandbox.com
     * because this url also will be used to display images coming from server api</p>
     * <p>Also see Application class of app module</p>
     *
     * @param baseUrl base url string for the web api call
     *                e.g http://api.dev.arbab.clicksandbox.com
     */
    public static synchronized void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    /**
     * <p>Functionality to set the web api version that will be added in base url
     * as postfix while calling request.</p>
     * <p>The format should be "/v1/"</p>
     * <p>Also see Application class of app module</p>
     *
     * @param apiVersion /v1/
     */
    public static synchronized void setApiVersion(String apiVersion) {
        mApiVersion = apiVersion;
    }

    /**
     * Return the base url for web api call
     */
    public static synchronized String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * Return the api version for web api call
     */
    public static synchronized String getApiVersion() {
        return mApiVersion;
    }
}
