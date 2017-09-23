package webconnect.com.webconnect;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import webconnect.com.webconnect.WebConstant;

/**
 * Created by amit on 10/9/17.
 */

public interface IAPIService {
    @Streaming
    @GET(WebConstant.sEND_POINT)
    Observable<Response<ResponseBody>> getFile(@Path("path_segment") String pathSegment, @QueryMap Map<String, Object> map);

    @GET(WebConstant.sEND_POINT)
    Observable<Response<Object>> get(@Path("path_segment") String pathSegment, @QueryMap Map<String, Object> map);

    @POST(WebConstant.sEND_POINT)
    Observable<Response<Object>> post(@Path("path_segment") String pathSegment, @Body Map<String, Object> map);

    @HTTP(method = "DELETE", path = WebConstant.sEND_POINT, hasBody = true)
    Observable<Response<Object>> delete(@Path("path_segment") String pathSegment, @Body Map<String, Object> map);

    @PUT(WebConstant.sEND_POINT)
    Observable<Response<Object>> put(@Path("path_segment") String pathSegment, @Body Map<String, Object> map);

    @Multipart
    @POST(WebConstant.sEND_POINT)
    Observable<Response<Object>> multipart(@Path("path_segment") String pathSegment, @Body Map<String, RequestBody> map);
}
