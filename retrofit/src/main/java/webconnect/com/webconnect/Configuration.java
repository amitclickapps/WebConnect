package webconnect.com.webconnect;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by amit on 10/8/17.
 */

public class Configuration implements Serializable {
    private static String BASE_URL = "";
    private static long CONNECT_TIMEOUT_MILLIS = 10 * 1000, READ_TIMEOUT_MILLIS = 20 * 1000;
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .create();

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static long getConnectTimeoutMillis() {
        return CONNECT_TIMEOUT_MILLIS;
    }

    public static long getReadTimeoutMillis() {
        return READ_TIMEOUT_MILLIS;
    }

    public static Gson getGson() {
        return gson;
    }

    public static class Builder {
        private String baseUrl = "";
        private long connectTimeOut = 10 * 1000, readTimeOut = 20 * 1000;

        public Builder baseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder connectTimeOut(@NonNull long connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        public Builder readTimeOut(@NonNull long readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        public void config() {
            BASE_URL = baseUrl;
            CONNECT_TIMEOUT_MILLIS = connectTimeOut;
            READ_TIMEOUT_MILLIS = readTimeOut;
        }
    }
}
