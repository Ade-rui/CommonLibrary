package com.ray.ray_core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.ray.ray_core.app.Mamoon;

/**
 * Created by wrf on 2018/1/22.
 */

public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources = Mamoon.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Mamoon.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
