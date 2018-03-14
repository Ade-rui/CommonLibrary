package com.ray.ray_core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Created by wrf on 2018/2/1.
 */

public class ItemBuilder {

    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    /*
    * 静态工厂
    * */
    static ItemBuilder builder(){
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bottomTabBean,BottomItemDelegate bottomItemDelegate){
        ITEMS.put(bottomTabBean,bottomItemDelegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean,BottomItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }

    public LinkedHashMap<BottomTabBean,BottomItemDelegate> build(){
        return ITEMS;
    }

}
