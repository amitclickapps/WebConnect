package webconnect.com.webconnect;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by amit on 10/8/17.
 */

public class ApiConfiguration implements Serializable {
    private static String sBASE_URL = "";
    private static long sCONNECT_TIMEOUT_MILLIS = 10 * 1000, sREAD_TIMEOUT_MILLIS = 20 * 1000;
    private static Gson sGSON = new GsonBuilder()
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .create();
    private static boolean sID_DEBUG = true;

    static String getBaseUrl() {
        return sBASE_URL;
    }

    static long getConnectTimeOut() {
        return sCONNECT_TIMEOUT_MILLIS;
    }

    static long getReadTimeOut() {
        return sREAD_TIMEOUT_MILLIS;
    }

    static Gson getGson() {
        return sGSON;
    }

    static boolean isDebug() {
        return sID_DEBUG;
    }

    public static class Builder {
        private String baseUrl = "";
        private long connectTimeOut = 10 * 1000, readTimeOut = 20 * 1000;
        private boolean isDebug = true;

        public Builder baseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder timeOut(long connectTimeOut, long readTimeOut) {
            this.connectTimeOut = connectTimeOut;
            this.readTimeOut = readTimeOut;
            return this;
        }

        public Builder debug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public void config() {
            sBASE_URL = baseUrl;
            sCONNECT_TIMEOUT_MILLIS = connectTimeOut;
            sREAD_TIMEOUT_MILLIS = readTimeOut;
            sID_DEBUG = isDebug;
        }
    }
}
