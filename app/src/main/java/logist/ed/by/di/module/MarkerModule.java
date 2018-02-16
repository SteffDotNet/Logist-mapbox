package logist.ed.by.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import logist.ed.by.mvp.service.MarkerService;

/**
 * Created by Egor on 14.02.2018.
 */

@Module
public class MarkerModule {

    @Provides
    @Singleton
    public MarkerService provideMarkerService(){
        return new MarkerService();
    }
}
