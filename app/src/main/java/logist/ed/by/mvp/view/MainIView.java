package logist.ed.by.mvp.view;

import android.content.Context;

import com.arellomobile.mvp.MvpView;
import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by Egor on 03.02.2018.
 */

public interface MainIView extends MvpView {
    void updateCamera(LatLng latLng);
    void showMenu(int type);
    void hideMenu();
    void startMarkerActivity();

}
