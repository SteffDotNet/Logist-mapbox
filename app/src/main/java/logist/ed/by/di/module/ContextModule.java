package logist.ed.by.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Egor on 13.02.2018.
 */

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }
}
