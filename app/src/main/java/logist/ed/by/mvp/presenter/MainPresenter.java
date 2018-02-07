package logist.ed.by.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mapbox.mapboxsdk.geometry.LatLng;

import logist.ed.by.mvp.view.MainIView;


/**
 * Created by Egor on 03.02.2018.
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainIView> implements MainIPresenter{
    @Override
    public void saveMarker(String title, String address, LatLng point) {

    }
}
