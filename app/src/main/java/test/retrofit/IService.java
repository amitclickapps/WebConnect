package test.retrofit;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by clickapps on 31/8/17.
 */

public interface IService {

    @GET(MainActivityModel.ENDPOINT_GET)
    Observable<Response<Object>> getPost(@Path("id") int id);
}

