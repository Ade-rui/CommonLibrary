package com.ray.ray_core.delegates.web.event;

import com.ray.ray_core.util.log.MammonLogger;

/**
 * Created by wrf on 2018/2/9.
 */

public class UndefineEvent extends Event {


    @Override
    public String execute(String params) {
        MammonLogger.i("mydata",params);
        return null;
    }
}
