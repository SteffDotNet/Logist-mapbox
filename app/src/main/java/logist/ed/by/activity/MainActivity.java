package logist.ed.by.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import logist.ed.by.R;
import logist.ed.by.dialog.DialogRemove;
import logist.ed.by.mvp.presenter.MainPresenter;
import logist.ed.by.mvp.service.MarkerService;
import logist.ed.by.mvp.view.MainIView;
import logist.ed.by.utils.Settings;


public class MainActivity extends MvpAppCompatActivity implements MainIView, View.OnClickListener, MapboxMap.OnMapClickListener, MapboxMap.OnMarkerClickListener, OnMapReadyCallback{
    private static final int REQUEST_MARKER = 1;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.fabAdd)
    FloatingActionButton buttonAdd;

    @BindView(R.id.fabRemove)
    FloatingActionButton buttonRemove;

    @InjectPresenter
    MainPresenter presenter;

    private MapboxMap mapboxMap;
    private Marker myLocation;
    private Marker selectMarker;
    private Polyline routePolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        buttonAdd.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);

    }


    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        mapboxMap.setOnMarkerClickListener(this);


        myLocation = mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(48.13863, 11.57603))
                .title("I")
        );

        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(4.814947,1.153674));
        points.add(new LatLng(4.814721,1.153643));
        points.add(new LatLng(4.814488,1.153492));
        points.add(new LatLng(4.813994,1.153407));
        points.add(new LatLng(4.813991,1.153472));
        points.add(new LatLng(4.814316,1.153483));
        points.add(new LatLng(4.814513,1.153537));
        points.add(new LatLng(4.814616,1.153611));
        points.add(new LatLng(4.814507,1.15414));
        points.add(new LatLng(4.814627,1.154269));
        points.add(new LatLng(4.814679,1.1544));
        points.add(new LatLng(4.814683,1.154517));
        points.add(new LatLng(4.814595,1.15496));
        points.add(new LatLng(4.814634,1.154985));
        points.add(new LatLng(4.81477,1.155214));
        points.add(new LatLng(4.814857,1.155303));
        points.add(new LatLng(4.815176,1.153717));
        points.add(new LatLng(4.816518,1.153787));
        points.add(new LatLng(4.814947,1.153674));

        mapboxMap.addPolyline(new PolylineOptions()
                .addAll(points)
                .color(Color.parseColor("#3bb2d0"))
                .width(2));

        updateCamera(myLocation.getPosition());

        presenter.updateMarkers(mapboxMap);
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        hideMenu();
        showMenu(Settings.MARKER_ADD);
        selectMarker = null;
        presenter.createMarker(mapboxMap, point);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if(myLocation.equals(marker)){
            return false;
        }
        presenter.removeCurrentMarker();
        selectMarker = marker;

        hideMenu();
        showMenu(Settings.MARKER_REMOVE);

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAdd:
                hideMenu();
                startMarkerActivity();
                break;

            case R.id.fabRemove:
                hideMenu();
                DialogRemove.show(this, () -> presenter.removeMarker(selectMarker));
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
    public void showMenu(int type) {
        switch (type){
            case Settings.MARKER_ADD:
                buttonAdd.setVisibility(View.VISIBLE);
                break;

            case Settings.MARKER_REMOVE:
                buttonRemove.setVisibility(View.VISIBLE);
                CountDownTimer timer = new CountDownTimer(5000, 5000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        hideMenu();
                    }
                }.start();
                break;
        }

    }

    @Override
    public void hideMenu() {
        buttonAdd.setVisibility(View.GONE);
        buttonRemove.setVisibility(View.GONE);
    }

    @Override
    public void startMarkerActivity() {
        presenter.stopMarkerTime();
        Intent intent = new Intent(this, MarkerActivity.class);
        startActivityForResult(intent, REQUEST_MARKER);
    }

    public boolean isMenuOpened() {
        return buttonAdd.getVisibility() == View.VISIBLE || buttonRemove.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_MARKER:
                if(resultCode == RESULT_OK){
                    updateRoute();
                }else if(resultCode == RESULT_CANCELED){
                    presenter.removeCurrentMarker();
                }
        }
    }

    @Override
    public void updateRoute(){
        presenter.updateRoute(mapboxMap, MarkerService.getInstance().getMarkersPositions());
    }

}
