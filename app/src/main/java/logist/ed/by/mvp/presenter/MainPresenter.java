package logist.ed.by.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

import logist.ed.by.mvp.service.MarkerService;
import logist.ed.by.mvp.view.MainIView;


/**
 * Created by Egor on 03.02.2018.
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainIView>{

    public void createMarker(MapboxMap mapboxMap, LatLng position){
        MarkerService.getInstance().createCurrentMarker(mapboxMap, position, this::hideMarkerMenu);
    }

    public  void removeCurrentMarker(){
        MarkerService.getInstance().removeCurrentMarker();
    }

    public void removeMarker(Marker marker) {
        MarkerService.getInstance().removeMarker(marker);
    }

    public void hideMarkerMenu(){
        getViewState().hideMenu(true);
    }

    public void stopMarkerTime(){
        MarkerService.getInstance().stopTimer();
    }

    public void updateMarkers(MapboxMap mapboxMap){
        List<Marker> newMarkers = new ArrayList<>();

        for(Marker m : MarkerService.getInstance().getAllMarkers()){
            Marker newMarker = mapboxMap.addMarker(new MarkerOptions()
                .position(m.getPosition())
                .title(m.getTitle())
                .snippet(m.getSnippet())
            );
            newMarkers.add(newMarker);
        }

        MarkerService.getInstance().setMarkers(newMarkers);
    }

    public void setCurrentMarker(Marker currentMarker) {
        MarkerService.getInstance().setCurrentMarker(currentMarker);
    }

}
