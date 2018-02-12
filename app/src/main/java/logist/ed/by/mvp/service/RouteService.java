package logist.ed.by.mvp.service;

import android.graphics.Color;
import android.util.Log;

import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.commons.geojson.LineString;
import com.mapbox.services.commons.models.Position;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import logist.ed.by.gson.DirectionResult;
import logist.ed.by.gson.Trip;

import static com.mapbox.services.Constants.PRECISION_5;

/**
 * Created by Egor on 12.02.2018.
 */

public class RouteService {

    private static RouteService instance;
    private final static String ACCESS_TOKEN = "pk.eyJ1IjoiZWdvcjk0IiwiYSI6ImNqZDc4cDFjcjE3b3AycXIybnRzMmx3NGoifQ.83ldmjO57BRCSKnnQx2qag";
    private Polyline polyline;

    public static RouteService getInstance(){
        if(instance == null){
            instance = new RouteService();
        }
        return instance;
    }

    public void findRoute(MapboxMap mapboxMap, LatLng origin, LatLng destination, List<LatLng> points){
        if(points.size() < 2) {
            return;
        }
        MapBoxApiService.getRetrofit().findRoute(getCoordinatesStr(points), ACCESS_TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result-> {
                    Log.i("TAG", "DirectionResult" +  result.getCode());
                    drawRoute(mapboxMap, result);
                }, error -> {
                    Log.e("TAG", "error  =  " + error.getMessage());
                });
    }

    private String getCoordinatesStr(List<LatLng> positions){
        StringBuilder sb = new StringBuilder();
        for (LatLng position : positions){
            sb.append(position.getLongitude());
            sb.append(',');
            sb.append(position.getLatitude());
            sb.append(';');
        }

        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    private void drawRoute(MapboxMap mapboxMap, DirectionResult directionResult){
        if(directionResult.getTrips().size()  == 0) {
            Log.i("TAG", "routes not fount");
            return;
        }

        Trip trip = directionResult.getTrips().get(0);
        List<LatLng> points = new ArrayList<>();
        LineString lineString = LineString.fromPolyline(trip.getGeometry(), PRECISION_5);
        List<Position> positions = lineString.getCoordinates();

        if (positions == null) {
            return;
        }

        for (int i = 0; i < positions.size(); i++) {
            points.add(new LatLng(
                    positions.get(i).getLatitude(),
                    positions.get(i).getLongitude()));
        }

        // Draw Points on MapView
        if(polyline != null){
            mapboxMap.removePolyline(polyline);
            polyline = null;
        }

        polyline = mapboxMap.addPolyline(new PolylineOptions()
                .addAll(points)
                .color(Color.parseColor("#ff00ff"))
                .width(2));
    }
}
