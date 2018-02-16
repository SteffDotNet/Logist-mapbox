package logist.ed.by.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import logist.ed.by.mvp.service.TimerService;

@Module
public class TimerModule {

    @Provides
    @Singleton
    public TimerService provideTimerService(){
        return new TimerService();
    }
}
