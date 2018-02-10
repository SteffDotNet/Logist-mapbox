package logist.ed.by.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import logist.ed.by.R;
import logist.ed.by.mvp.presenter.MainPresenter;
import logist.ed.by.mvp.view.MainIView;


public class MainActivity extends MvpAppCompatActivity implements MainIView, View.OnClickListener, MapboxMap.OnMapClickListener, OnMapReadyCallback{
    private static final int REQUEST_MARKER = 1;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.markerMenu)
    ViewGroup markerMenu;

    @BindView(R.id.markerAdd)
    View itemAdd;

    @BindView(R.id.markerEdit)
    View itemEdit;

    @BindView(R.id.markerRemove)
    View itemRemove;

    @InjectPresenter
    MainPresenter presenter;

    private MapboxMap mapboxMap;
    private Marker myLocation;
    private Marker currentMarker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        itemAdd.setOnClickListener(this);
        itemEdit.setOnClickListener(this);
        itemRemove.setOnClickListener(this);

    }


    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        Log.i("TAG", "ready");

        myLocation = mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(48.13863, 11.57603))
                .title("I")
        );

        updateCamera(myLocation.getPosition());

        presenter.updateMarkers(mapboxMap);
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        if(!isMenuOpened()){
            showMenu();
        }

        presenter.createMarker(mapboxMap, point);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.markerAdd:
                hideMenu(false);
                startMarkerActivity();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapboxMap != null) {
            mapboxMap.removeOnMapClickListener(this);
        }
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    //MapIView actions

    @Override
    public void updateCamera(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .build();
        mapboxMap.setCameraPosition(cameraPosition);
    }

    @Override
    public void showMenu() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.menu_open);
        markerMenu.setVisibility(View.VISIBLE);
        markerMenu.startAnimation(anim);

    }

    @Override
    public void hideMenu(boolean isAnim) {

        if(!isAnim) {
            markerMenu.setVisibility(View.GONE);
            return;
        }

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.menu_close);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                markerMenu.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        markerMenu.startAnimation(anim);

    }

    @Override
    public void startMarkerActivity() {
        presenter.stopMarkerTime();
        Intent intent = new Intent(this, MarkerActivity.class);
        startActivityForResult(intent, REQUEST_MARKER);
    }

    public boolean isMenuOpened() {
        return markerMenu.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_MARKER:
                if(resultCode == RESULT_OK){

                }else if(resultCode == RESULT_CANCELED){
                    presenter.removeCurrentMarker(mapboxMap);
                }
        }
    }


}
