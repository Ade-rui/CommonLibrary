package com.ray.ray_core.util.callback;

import android.support.annotation.Nullable;

/**
 * Created by wrf on 2018/3/10.
 */

public interface IGlobalCallBack<T> {

    void executeCallback(@Nullable T t);

}
