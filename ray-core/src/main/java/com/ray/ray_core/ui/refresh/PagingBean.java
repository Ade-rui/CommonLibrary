package com.ray.ray_core.ui.refresh;

/**
 * Created by wrf on 2018/2/7.
 */

public class PagingBean {

    //当前第几页
    public int pageIndx;
    //总数据条数
    public int total;
    //一页显示几条数据
    public int pageSize;
    //当前已经显示几条数据
    public int currentCount;
    //显示数据
    public int delayed;

    PagingBean addIndex() {
        pageIndx++;
        return this;
    }
}
