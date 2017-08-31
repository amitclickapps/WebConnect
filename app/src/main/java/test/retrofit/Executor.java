package test.retrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import webconnect.com.webconnect.Callback;
import webconnect.com.webconnect.WebConnect;
import webconnect.com.webconnect.WebParam;

/**
 * Created by clickapps on 31/8/17.
 */

public enum Executor {

    POSTS {
        @Override
        void execute(WebParam param) {
            int id = Integer.valueOf((String) param.getRequestParam().get("id"));
            WebConnect.connect(IService.class, param)
                    .getPost(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Callback<>(param));
        }
    };

    abstract void execute(WebParam param);


}
