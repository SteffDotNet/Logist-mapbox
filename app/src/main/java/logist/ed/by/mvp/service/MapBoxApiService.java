package logist.ed.by.mvp.service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import logist.ed.by.mvp.service.retrofit.MapBoxApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Egor on 10.02.2018.
 */

public class MapBoxApiService {
    private static Retrofit retrofit;

    public static MapBoxApi getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://api.mapbox.com/")
                    .build();
        }

        return retrofit.create(MapBoxApi.class);
    }
}
