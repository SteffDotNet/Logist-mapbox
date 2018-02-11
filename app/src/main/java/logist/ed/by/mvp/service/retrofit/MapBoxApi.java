package logist.ed.by.mvp.service.retrofit;

import io.reactivex.Observable;
import logist.ed.by.gson.DirectionResult;
import logist.ed.by.gson.GeocodingResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Egor on 10.02.2018.
 */

public interface MapBoxApi {
    @GET("/geocoding/v5/mapbox.places/{position}.json")
    Observable<GeocodingResult> searchPlace(@Path("position") String pos, @Query("types") String type, @Query("access_token") String token);

    @GET("optimized-trips/v1/mapbox/driving/{coordinates}")
    Observable<DirectionResult> findRoute(@Path("coordinates") String coordinates, @Query("access_token") String token);
}

/*    # request an optimized car roundtrip in Berlin with four coordinates, starting at the first coordinate pair, ending at the coordinate pair
      curl "https://api.mapbox.com/optimized-trips/v1/mapbox/driving/13.388860,52.517037;13.397634,52.529407;13.428555,52.523219;13.418555,52.523215?source=first&destination=last&roundtrip=true&access_token=your-access-token*"
 */



