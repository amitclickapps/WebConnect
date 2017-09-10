package test.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import webconnect.com.webconnect.WebConnect;
import webconnect.com.webconnect.WebHandler;
import webconnect.com.webconnect.WebParam;

/**
 * Created by clickapps on 31/8/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.button)
    Button getBtn;

    public static final String ENDPOINT_POST = "posts/{id}";
    public static final String ENDPOINT_BASE = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button) {
            Map<String, String> requestMap = new LinkedHashMap<>();
//            requestMap.put("id", "1");
            WebConnect.with(this, ENDPOINT_POST, "posts")
                    .httpType(WebParam.HttpType.GET)
                    .pathSegmentParam("1")
                    .callback(new WebHandler.OnWebCallback() {
                        @Override
                        public <T> void onSuccess(@Nullable T object, int taskId, Response response) {
                            if (object != null) {
                                Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public <T> void onError(@Nullable T object, String error, int taskId) {
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }).requestParam(requestMap).connect();
//            Executor.POSTS.execute(param);
        } else {
            Log.i(getLocalClassName(), "No clickHandled");
        }
    }
}
