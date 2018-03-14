package com.ray.ray_core.util.timer;

import java.util.TimerTask;

/**
 * Created by wrf on 2018/1/25.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener listener;

    public BaseTimerTask() {
    }

    public BaseTimerTask(ITimerListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        if(listener!=null){
            listener.onTimer();
        }
    }
}
