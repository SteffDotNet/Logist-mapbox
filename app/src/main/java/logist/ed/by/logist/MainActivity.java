package logist.ed.by.logist;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;


import logist.ed.by.logist.mvp.view.MainIView;

public class MainActivity extends MvpAppCompatActivity implements MainIView {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.map_key));

        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

    }

    @Override
    public void addMapPoint() {

    }
}
