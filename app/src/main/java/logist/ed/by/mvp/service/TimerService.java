package logist.ed.by.mvp.service;

import android.os.CountDownTimer;
import logist.ed.by.utils.Constants;

public class TimerService {
    public interface TimerListener{
        void finish();
    }

    private CountDownTimer timer;

    public void start(TimerListener listener){
        timer = new CountDownTimer(Constants.INTERVAL, Constants.DURATION) {
            @Override
            public void onTick(long l) {
                //TO DO
            }

            @Override
            public void onFinish() {
                if(listener != null){
                    listener.finish();
                }
            }
        } .start();
    }

    public void stop(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

}
