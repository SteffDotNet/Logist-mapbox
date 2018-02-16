package logist.ed.by.mvp.presenter;

import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import javax.inject.Inject;
import logist.ed.by.LogistApp;
import logist.ed.by.mvp.service.MarkerService;
import logist.ed.by.mvp.view.MarkerIView;

@InjectViewState
public class MarkerPresenter extends MvpPresenter<MarkerIView> {

    @Inject MarkerService markerService;

    public MarkerPresenter() {
        LogistApp.getAppComponent().inject(this);
    }

    public void saveMarker(Marker marker){
        markerService.addMarker(marker);
    }

    public void setCurrentMarker(Marker marker){
        markerService.setCurrentMarker(marker);
    }

    public Marker getCurrentMarker(){
        return markerService.getCurrentMarker();
    }

    public void searchPlace(LatLng position, EditText editText){
        //GeocodingService.getInstance().searchPlace(position, editText);
    }
}
