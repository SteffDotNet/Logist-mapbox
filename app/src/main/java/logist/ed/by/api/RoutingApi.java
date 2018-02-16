package logist.ed.by.api;

import io.reactivex.Observable;
import logist.ed.by.gson.DirectionResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RoutingApi {

    @GET("optimized-trips/v1/mapbox/driving/{coordinates}")
    Observable<DirectionResult> findRoute(@Path("coordinates") String coordinates, @Query("access_token") String token);
}
