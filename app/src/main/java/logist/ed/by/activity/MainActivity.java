package logist.ed.by.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import logist.ed.by.R;
import logist.ed.by.mvp.presenter.MainPresenter;
import logist.ed.by.mvp.view.MainIView;


public class MainActivity extends MvpAppCompatActivity implements MainIView, OnMapReadyCallback, MapboxMap.OnMapClickListener,View.OnClickListener {
    private static final int REQUEST_MARKER = 1;

    @InjectPresenter
    public MainPresenter presenter;
    private MapView mapView;
    private MapboxMap mapboxMap;

    private Marker currentMarker;
    private CountDownTimer timerMarker;

    @BindView(R.id.markerMenu)
    public View markerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.map_key));
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        if (!isMenuOpened()){
            showMenu();
        }

        showMarker(point);
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
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
        if(timerMarker != null){
            timerMarker.cancel();
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.markerAdd:
                hideMenu();
                startMarkerActivity();
                removeMarker();
                break;
        }
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
    public void showMarker(LatLng position) {
        if(timerMarker != null){
            timerMarker.cancel();
            timerMarker = null;
        }

        if(currentMarker == null){
            currentMarker = mapboxMap.addMarker(new MarkerOptions().position(position));
        }else{
            currentMarker.setPosition(position);
        }

        timerMarker = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                mapboxMap.removeMarker(currentMarker);
                currentMarker = null;
                hideMenu();
            }
        }.start();
    }

    @Override
    public void removeMarker() {
        if(timerMarker != null){
            timerMarker.cancel();
            timerMarker = null;
        }

        if(currentMarker != null){
            mapboxMap.removeMarker(currentMarker);
            currentMarker = null;
        }
    }

    @Override
    public void startMarkerActivity() {
        Intent intent = new Intent(this, MarkerActivity.class);
        intent.putExtra("lat", currentMarker.getPosition().getLatitude());
        intent.putExtra("lng", currentMarker.getPosition().getLongitude());
        startActivityForResult(intent, REQUEST_MARKER);
    }

    @Override
    public void showMenu() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.menu_open);
        markerMenu.setVisibility(View.VISIBLE);
        markerMenu.startAnimation(anim);
    }

    @Override
    public void hideMenu() {
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


    public boolean isMenuOpened() {
        return markerMenu.getVisibility() == View.VISIBLE;
    }

}
