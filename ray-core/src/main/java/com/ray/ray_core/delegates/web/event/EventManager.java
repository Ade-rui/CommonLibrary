package com.ray.ray_core.delegates.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by wrf on 2018/2/9.
 */

public class EventManager {

    private static final HashMap<String,Event> EVENTS = new HashMap<>();

    private EventManager(){
    }

    private static class Holder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        return Holder.INSTANCE;
    }

    public EventManager addEvent(@NonNull String name,@NonNull Event event){
        EVENTS.put(name,event);
        return this;
    }

    public Event create(String name){
        Event event = EVENTS.get(name);
        if(event == null){
            event = new UndefineEvent();
        }
        return event;
    }

}
