package logist.ed.by.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;

import javax.inject.Inject;

import logist.ed.by.LogistApp;
import logist.ed.by.mvp.service.MarkerService;
import logist.ed.by.mvp.service.RouteService;
import logist.ed.by.mvp.service.TimerService;
import logist.ed.by.mvp.view.MainIView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainIView>{

    @Inject TimerService timerService;

    @Inject MarkerService markerService;

    public MainPresenter() {
        LogistApp.getAppComponent().inject(this);
    }

    public void startTimer(){
        timerService.start(this::finishTimer);
    }

    public void stopTimer(){
        timerService.stop();
    }

    public void finishTimer(){
        getViewState().removeMarker();
        setCurrentMarker(null);
        getViewState().hideMenu();
    }

    public void setCurrentMarker(Marker marker){
        markerService.setCurrentMarker(marker);
    }

    public Marker getCurrentMarker(){
        return markerService.getCurrentMarker();
    }

    public void updateMarkers(MapboxMap mapboxMap){
        /*List<Marker> newMarkers = new ArrayList<>();

        for(Marker m : MarkerService.getInstance().getAllMarkers()){
            Marker newMarker = mapboxMap.addMarker(new MarkerOptions()
                .position(m.getPosition())
                .title(m.getTitle())
                .snippet(m.getSnippet())
            );
            newMarkers.add(newMarker);
        }

        MarkerService.getInstance().setMarkers(newMarkers);*/
    }

    public void updateRoute(MapboxMap mapboxMap, List<LatLng> points){
        RouteService.getInstance().findRoute(mapboxMap, null, null, points);
    }

}
