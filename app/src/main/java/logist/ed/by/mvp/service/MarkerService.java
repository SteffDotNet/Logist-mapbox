package logist.ed.by.mvp.service;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import java.util.ArrayList;
import java.util.List;

public class MarkerService{

    private List<Marker> markers;
    private Marker currentMarker;

    public MarkerService() {
        markers = new ArrayList<>();
    }

    public Marker getCurrentMarker() {
        return currentMarker;
    }

    public void setCurrentMarker(Marker currentMarker) {
        this.currentMarker = currentMarker;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    public List<LatLng> getMarkersPositions(){
        List<LatLng> positions = new ArrayList<>();
        for(Marker m : markers){
            positions.add(m.getPosition());
        }

        return positions;
    }

    public void addMarker(Marker marker) {
        markers.add(marker);
    }

    public void removeMarker(Marker marker) {
        markers.remove(marker);
        marker.remove();
    }


}
