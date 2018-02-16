package logist.ed.by.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import logist.ed.by.api.GeocodingApi;
import logist.ed.by.api.RoutingApi;
import retrofit2.Retrofit;

@Module(includes = RetrofitModule.class)
public class ApiModule {

    @Provides
    @Singleton
    public GeocodingApi provideGeocodingApi(Retrofit retrofit) {
        return retrofit.create(GeocodingApi.class);
    }

    @Provides
    @Singleton
    public RoutingApi provideRoutingApi(Retrofit retrofit) {
        return retrofit.create(RoutingApi.class);
    }

}
