package logist.ed.by.mvp.service;

import android.util.Log;
import android.widget.EditText;

import com.mapbox.mapboxsdk.geometry.LatLng;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import logist.ed.by.gson.GeocodingResult;
import logist.ed.by.mvp.service.retrofit.MapBoxApi;

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
        String coordinates = position.getLatitude() + "," + position.getLongitude();
        String type = "address";

        MapBoxApiService.getRetrofit().searchPlace(coordinates, type, ACCESS_TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    et.setText(result.getFeatures().get(0).getAddress());
                }, error -> {
                    Log.e("MyLog", error.getMessage());
                });
    }

  /*  private void onSuccess(GeocodingResult result){
        Log.e("MyLog", "geocoding success = "  + result.getFeatures().get(0).getPlaceName());

    }

    private void onFailure(Throwable throwable){
        Log.e("MyLog", "geocoding error = " + throwable.getMessage());
    }*/


}
