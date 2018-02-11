package logist.ed.by.mvp.presenter;

import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import logist.ed.by.mvp.service.GeocodingService;
import logist.ed.by.mvp.service.MarkerService;
import logist.ed.by.mvp.view.MarkerIView;

/**
 * Created by Egor on 10.02.2018.
 */

@InjectViewState
public class MarkerPresenter extends MvpPresenter<MarkerIView> {

    public  void removeCurrentMarker(MapboxMap mapboxMap){
        MarkerService.getInstance().removeCurrentMarker();
    }

    public void saveMarker(Marker marker){
        MarkerService.getInstance().addMarker(marker);
    }



    public void searchPlace(LatLng position, EditText editText){
        GeocodingService.getInstance().searchPlace(position, editText);
    }
}
