package logist.ed.by.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

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
import logist.ed.by.ui.dialog.DialogRemove;
import logist.ed.by.utils.Constants;


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
    private Marker currentMarker;


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

        updateCamera(myLocation.getPosition());
        //presenter.updateMarkers(mapboxMap);
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        createNewMarker(point);
        showMenu(Constants.MARKER_ADD);
        presenter.stopTimer();
        presenter.startTimer();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
       /* if(myLocation.equals(marker)){
            return false;
        }
        presenter.removeCurrentMarker();
        selectMarker = marker;

        hideMenu();
        showMenu(Constants.MARKER_REMOVE);*/

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAdd:
                hideMenu();
                presenter.stopTimer();
                presenter.setCurrentMarker(currentMarker);
                startMarkerActivity();
                break;

            case R.id.fabRemove:
                hideMenu();
               // DialogRemove.show(this, () -> presenter.removeMarker(selectMarker));
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
    public void createNewMarker(LatLng latLng) {
        if(currentMarker == null){
            currentMarker = mapboxMap.addMarker(new MarkerOptions().position(latLng));
        }else{
            currentMarker.setPosition(latLng);
        }
    }

    @Override
    public void removeMarker() {
        if(currentMarker != null){
            mapboxMap.removeMarker(currentMarker);
            currentMarker = null;
        }
    }

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
            case Constants.MARKER_ADD:
                buttonAdd.setVisibility(View.VISIBLE);
                break;

            case Constants.MARKER_REMOVE:
                buttonRemove.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(this, MarkerActivity.class);
        startActivityForResult(intent, REQUEST_MARKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_MARKER:
                if(resultCode == RESULT_OK){
                    currentMarker = null;
                    //updateRoute();
                }else if(resultCode == RESULT_CANCELED){
                    removeMarker();
                }
        }
    }

    @Override
    public void updateRoute(){
        //presenter.updateRoute(mapboxMap, MarkerService.getInstance().getMarkersPositions());
    }

}
