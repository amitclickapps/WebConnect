package webconnect.com.webconnect;

import android.net.TrafficStats;

import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by clickapps on 29/12/17.
 */
@SuppressWarnings("unchecked")
public class RxObservable {


    static final class SimpleANObservable<T> extends Observable<T> {

        private WebParam param;
        private final Call originalCall;

        SimpleANObservable(WebParam param, Call call) {
            this.param = param;
            this.originalCall = call;
        }

        @Override
        protected void subscribeActual(Observer<? super T> observer) {
            Call call = originalCall.clone();
            observer.onSubscribe(new ANDisposable(call));
            final long startTime = System.currentTimeMillis();
            final long startBytes = TrafficStats.getTotalRxBytes();
            try {
                Response okHttpResponse = call.execute();
                final long timeTaken = System.currentTimeMillis() - startTime;
                T object;
                if (okHttpResponse.isSuccessful()) {
                    if (okHttpResponse.body() != null) {
                        if (param.model != null) {
                            object = (T) new Gson().fromJson(okHttpResponse.body().string(), param.model);
                        } else {
                            object = (T) new Gson().fromJson(okHttpResponse.body().string(), Object.class);
                        }
                    } else {
                        object = (T) "";
                    }
                    observer.onNext(object);
                } else {
                    if (okHttpResponse.body() != null) {
                        observer.onError(new Throwable(okHttpResponse.body().string()));
                    } else {
                        observer.onError(new Throwable(""));
                    }

                }
            } catch (Exception e) {
                observer.onError(e);
            } finally {
                observer.onComplete();
            }
        }

    }

    static final class DownloadANObservable<T> extends Observable<T> {

        private WebParam param;
        private final Call originalCall;

        DownloadANObservable(WebParam param, Call call) {
            this.param = param;
            this.originalCall = call;
        }

        @Override
        protected void subscribeActual(Observer<? super T> observer) {
            Call call = originalCall.clone();
            observer.onSubscribe(new ANDisposable(call));
            final long startTime = System.currentTimeMillis();
            final long startBytes = TrafficStats.getTotalRxBytes();

            try {
                Response okHttpResponse = call.execute();
                final long timeTaken = System.currentTimeMillis() - startTime;
                T object = null;
                if (okHttpResponse.isSuccessful()) {
//                    if (okHttpResponse.body() != null) {
//                        if (param.model != null) {
//                            object = (T) new Gson().fromJson(okHttpResponse.body().string(), param.model);
//                        } else {
//                            object = (T) new Gson().fromJson(okHttpResponse.body().string(), Object.class);
//                        }
//                    } else {
//                        object = (T) "";
//                    }
                    observer.onNext(object);
                } else {
                    if (okHttpResponse.body() != null) {
                        observer.onError(new Throwable(okHttpResponse.body().string()));
                    } else {
                        observer.onError(new Throwable(""));
                    }

                }
            } catch (Exception e) {
                observer.onError(e);
            } finally {
                observer.onComplete();
            }
        }
    }

    private static final class ANDisposable implements Disposable {

        private final Call call;

        private ANDisposable(Call call) {
            this.call = call;
        }

        @Override
        public void dispose() {
            this.call.cancel();
        }

        @Override
        public boolean isDisposed() {
            return this.call.isCanceled();
        }
    }
}