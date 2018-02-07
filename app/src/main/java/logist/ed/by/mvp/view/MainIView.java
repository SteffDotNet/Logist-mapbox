package logist.ed.by.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by Egor on 03.02.2018.
 */

public interface MainIView extends MvpView {
    void updateCamera(LatLng latLng);

    void showMenu();
    void hideMenu();
    void startMarkerActivity();
    void showMarker(LatLng position);
    void removeMarker();

}
