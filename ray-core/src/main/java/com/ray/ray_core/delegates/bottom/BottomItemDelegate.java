package com.ray.ray_core.delegates.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ray.ray_core.delegates.BaseDelegate;
import com.ray.ray_core.delegates.MammonDelegate;

/**
 * Created by wrf on 2018/2/1.
 * 与tab对应的每一个fragment
 */

public abstract class BottomItemDelegate extends MammonDelegate {

    private static final long WAIT_TIME = 2000l;
    private long TOUCH_TIME;

    @Override
    public boolean onBackPressedSupport() {
        if(TOUCH_TIME > 0 && (System.currentTimeMillis() - TOUCH_TIME) < WAIT_TIME){
            _mActivity.finish();
        }else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(getContext(), "双击退出", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
