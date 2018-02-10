package logist.ed.by.mvp.service;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;

/**
 * Created by Egor on 08.02.2018.
 */

public interface MarkerDaoService {

    interface MenuListener{
        void hide();
    }

    Marker createCurrentMarker(MapboxMap mapboxMap, LatLng position, MenuListener listener);
    void removeCurrentMarker(MapboxMap mapboxMap);

    void addMarker(Marker marker);
    void removeMarker(Marker marker);
    List<Marker> getAllMarkers();

    void stopTimer();
}
