package logist.ed.by.mvp.service;

import android.util.Log;
import android.widget.EditText;

import com.mapbox.mapboxsdk.geometry.LatLng;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Egor on 10.02.2018.
 */

public class GeocodingService {
    private static GeocodingService instance;
    private final static String ACCESS_TOKEN = "pk.eyJ1IjoiZWdvcjk0IiwiYSI6ImNqZDc4cDFjcjE3b3AycXIybnRzMmx3NGoifQ.83ldmjO57BRCSKnnQx2qag";

    public static GeocodingService getInstance(){
        if(instance == null){
            instance = new GeocodingService();
        }
        return instance;
    }

    public void searchPlace(LatLng position, EditText et){
        String coordinates = position.getLongitude() + "," + position.getLatitude();
        String type = "address";

        MapBoxApiService.getRetrofit().searchPlace(coordinates, type, ACCESS_TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    if(result.getFeatures().size() > 0){
                        int index = result.getFeatures().get(0).getPlaceName().indexOf(",");
                        et.setText(result.getFeatures().get(0).getPlaceName().substring(0, index));
                    }
                }, error -> {
                    Log.e("MyLog", error.getMessage());
                });

    }

}
