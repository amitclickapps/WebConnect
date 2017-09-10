package webconnect.com.webconnect;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by amit on 10/9/17.
 */

public interface IAPIService {

    @GET(WebConstant.sEND_POINT)
    Observable<Response<Object>> get(@Path("path_segment") String pathSegment,
                                     @Path("parameter") String parameter, @QueryMap Map<String, Object> map);

    @POST(WebConstant.sEND_POINT)
    Observable<Response<Object>> post(@Path("path_segment") String pathSegment,
                                      @Path("parameter") String parameter, @Body Map<String, Object> map);

    @DELETE(WebConstant.sEND_POINT)
    Observable<Response<Object>> delete(@Path("path_segment") String pathSegment,
                                        @Path("parameter") String parameter, @Body Map<String, Object> map);

    @PUT(WebConstant.sEND_POINT)
    Observable<Response<Object>> put(@Path("path_segment") String pathSegment,
                                     @Path("parameter") String parameter, @Body Map<String, Object> map);
    @Multipart
    @POST(WebConstant.sEND_POINT)
    Observable<Response<Object>> multipart(@Path("path_segment") String pathSegment,
                                      @Path("parameter") String parameter, @Body Map<String, RequestBody> map);
}
