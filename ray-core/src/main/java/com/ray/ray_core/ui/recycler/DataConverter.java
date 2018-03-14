package com.ray.ray_core.ui.recycler;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by wrf on 2018/2/5.
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJson(String json){
        mJsonData = json;
         return this;
    }

    public String jsonData(){
        if(TextUtils.isEmpty(mJsonData)){
            throw new NullPointerException("json data is null!");
        }
        return mJsonData;
    }

    public void clear(){
        ENTITIES.clear();
    }
}
