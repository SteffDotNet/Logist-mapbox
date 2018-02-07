package logist.ed.by.mvp.presenter;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by Egor on 03.02.2018.
 */

public interface MainIPresenter {
    void saveMarker(String title, String address, LatLng point);
}
