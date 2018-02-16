package logist.ed.by;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

import logist.ed.by.di.AppComponent;
import logist.ed.by.di.DaggerAppComponent;
import logist.ed.by.utils.Constants;

/**
 * Created by Egor on 09.02.2018.
 */

public class LogistApp extends Application {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Mapbox.getInstance(getApplicationContext(), Constants.MAPBOX_ACCESS_TOKEN);
        initDagger();
    }

    public void initDagger(){
        appComponent = DaggerAppComponent.builder().build();
    }
}
