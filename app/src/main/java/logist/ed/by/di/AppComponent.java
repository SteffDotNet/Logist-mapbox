package logist.ed.by.di;

import javax.inject.Singleton;

import dagger.Component;
import logist.ed.by.di.module.MarkerModule;
import logist.ed.by.di.module.TimerModule;
import logist.ed.by.mvp.presenter.MainPresenter;
import logist.ed.by.mvp.presenter.MarkerPresenter;
import logist.ed.by.ui.activity.MainActivity;
import logist.ed.by.di.module.ApiModule;
import logist.ed.by.di.module.ContextModule;

@Singleton
@Component(modules = {ContextModule.class, TimerModule.class, MarkerModule.class, ApiModule.class})
public interface AppComponent  {

    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(MarkerPresenter markerPresenter);

}
