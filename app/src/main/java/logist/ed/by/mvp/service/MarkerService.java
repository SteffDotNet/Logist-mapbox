package logist.ed.by.mvp.service;

import android.os.CountDownTimer;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 08.02.2018.
 */

public class MarkerService implements MarkerDaoService{
    public static MarkerService instance;

    private Marker currentMarker;
    private List<Marker> markers;
    private CountDownTimer timer;



    public MarkerService() {
        markers = new ArrayList<>();
    }

    public Marker getCurrentMarker() {
        return currentMarker;
    }

    public void setCurrentMarker(Marker currentMarker) {
        this.currentMarker = currentMarker;
    }

    public static MarkerService getInstance(){
        if(instance == null){
            instance = new MarkerService();
        }
        return instance;
    }

    @Override
    public Marker createCurrentMarker(MapboxMap mapboxMap, LatLng position, MenuListener listener) {
        if(timer != null){
            timer.cancel();
            timer = null;
        }

        timer = new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(currentMarker != null){
                    mapboxMap.removeMarker(currentMarker);
                    currentMarker = null;

                    if(listener != null){
                        listener.hide();
                    }
                }
            }
        }.start();

        if(currentMarker == null){
            currentMarker = mapboxMap.addMarker(new MarkerOptions().position(position));
        }else{
            currentMarker.setPosition(position);
        }

        return currentMarker;
    }

    @Override
    public void removeCurrentMarker(MapboxMap mapboxMap) {
        if(timer != null){
            timer.cancel();
            timer = null;
        }

        if(currentMarker != null){
            mapboxMap.removeMarker(currentMarker);
            currentMarker = null;
        }
    }

    @Override
    public void addMarker(Marker marker) {
        markers.add(marker);
    }

    @Override
    public void removeMarker(Marker marker) {
        markers.remove(marker);
    }

    @Override
    public List<Marker> getAllMarkers() {
        return markers;
    }

    @Override
    public void stopTimer() {
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

}
