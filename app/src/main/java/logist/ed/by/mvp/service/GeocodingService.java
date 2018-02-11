package logist.ed.by.mvp.service;

import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;

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

 /*       List<LatLng> list = new ArrayList<>();
        list.add(position);
        list.add(position);
        list.add(position);*/

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
        Log.i("TAG", "coordinates = " + sb.toString());

        return sb.toString();
    }

    private void drawRoute(MapboxMap mapboxMap, DirectionResult directionResult){
        //cd`eHeumeAqDAx@wNyGuNb@kC|AhAw@rp@tB`IbExCnh@pEx|BxGvSrIz{@|D~KpDjPb]r@iAoPmZsHuDu`AcFaQiIq_C}G_YeDuJ{GuH}MOoWpAdAkAjFt@bK|C}C
        if(directionResult.getTrips().size()  == 0) {
            Log.i("TAG", "routes not fount");
            return;
        }

        Log.i("TAG", "get route");
        Trip trip = directionResult.getTrips().get(0);

        Log.i("TAG", "geometry" + trip.getGeometry());

        // Convert LineString coordinates into LatLng[]
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

        if (points != null) {
            //mapboxMap.removeAnnotation(points);
        }

        Log.i("TAG", "points = " + points.size());
        for(LatLng p : points){
            Log.i("TAG", "points.add(new LatLang(" + p.getLatitude() + "," + p.getLongitude() + "));");
        }

        // Draw Points on MapView
        mapboxMap.addPolyline(new PolylineOptions()
                .addAll(points)
                .color(Color.parseColor("#ff00ff"))
                .width(2));
    }
}
