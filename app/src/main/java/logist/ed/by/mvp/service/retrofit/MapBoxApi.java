package logist.ed.by.mvp.service.retrofit;

import io.reactivex.Observable;
import logist.ed.by.gson.GeocodingResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Egor on 10.02.2018.
 */

public interface MapBoxApi {
    //geocoding/v5/mapbox.places/-77.050,38.889.json?types=address&access_token=
    @GET("/geocoding/v5/mapbox.places/{position}.json")
    Observable<GeocodingResult> searchPlace(@Path("position") String pos, @Query("types") String type, @Query("access_token") String token);
}
