package logist.ed.by;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

/**
 * Created by Egor on 09.02.2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Mapbox.getInstance(getApplicationContext(), getResources().getString(R.string.map_key));
    }
}
