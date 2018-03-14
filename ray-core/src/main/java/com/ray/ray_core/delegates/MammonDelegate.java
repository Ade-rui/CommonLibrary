package com.ray.ray_core.delegates;

/**
 * Created by wrf on 2018/1/18.
 */

public abstract class MammonDelegate extends PermissionCheckerDelegate {

    public <T extends MammonDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }

}
