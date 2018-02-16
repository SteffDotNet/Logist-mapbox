package logist.ed.by.api;

import io.reactivex.Observable;
import logist.ed.by.gson.GeocodingResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Egor on 15.02.2018.
 */

public interface GeocodingApi {

    @GET("/geocoding/v5/mapbox.places/{position}.json")
    Observable<GeocodingResult> searchPlace(@Path("position") String pos, @Query("types") String type, @Query("access_token") String token);
}
