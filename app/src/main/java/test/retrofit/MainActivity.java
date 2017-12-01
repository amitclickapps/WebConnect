package test.retrofit;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Locale;
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
    @BindView(R.id.button1)
    Button postBtn;
    @BindView(R.id.button2)
    Button putBtn;
    @BindView(R.id.button3)
    Button deleteBtn;

    public static final String ENDPOINT_GET = "users";
    public static final String ENDPOINT_POST = "users";
    public static final String ENDPOINT_PUT = "users/740";
    public static final String ENDPOINT_BASE = "https://reqres.in/api/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getBtn.setOnClickListener(this);
        postBtn.setOnClickListener(this);
        putBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button) {
            // https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg
            // http://api.staging.moh.clicksandbox1.com:8080/upload/magazins/8/original/624996-pixelponew.jpg?1505885452
            File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            Map<String, String> requestMap = new LinkedHashMap<>();
            WebConnect.with(this, ENDPOINT_PUT)
//                    .downloadFile(file)
                    .progressListener(new WebHandler.ProgressListener() {
                        @Override
                        public void update(long bytesRead, long contentLength, boolean done) {
                            String value = String.format(Locale.ENGLISH, "%d%% done\n", (100 * bytesRead) / contentLength);
                            Log.i(getLocalClassName(), value);
                        }
                    })
                    .requestParam(requestMap)
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
                    }).connect();
//            Executor.POSTS.execute(param);
        } else if (id == R.id.button1) {
            Map<String, String> requestMap = new LinkedHashMap<>();
            requestMap.put("name", "Amit");
            requestMap.put("job", "manager");
            WebConnect.with(this, ENDPOINT_POST)
                    .httpType(WebParam.HttpType.POST)
                    .requestParam(requestMap)
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
                    }).connect();

        } else if (id == R.id.button2) {
            Map<String, String> requestMap = new LinkedHashMap<>();
            requestMap.put("name", "Amit Singh");
            requestMap.put("job", "manager");
            WebConnect.with(this, ENDPOINT_PUT)
                    .httpType(WebParam.HttpType.PUT)
                    .requestParam(requestMap)
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
                    }).connect();

        } else if (id == R.id.button3) {
            Map<String, String> requestMap = new LinkedHashMap<>();
            requestMap.put("name", "Amit Singh");
            requestMap.put("job", "manager");
            WebConnect.with(this, ENDPOINT_PUT)
                    .httpType(WebParam.HttpType.DELETE)
                    .requestParam(requestMap)
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
                    }).connect();
        } else {
            Log.i(getLocalClassName(), "No clickHandled");
        }


    }
}
