package com.ray.ray_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ray.ray_core.app.Mamoon;
import com.ray.ray_core.net.RestClient;
import com.ray.ray_core.net.callback.ISuccess;
import com.ray.ray_core.ui.recycler.DataConverter;
import com.ray.ray_core.ui.recycler.MultipleRecyclerAdapter;
import com.ray.ray_core.util.log.MammonLogger;

/**
 * Created by wrf on 2018/2/5.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout refreshLayout;
    private final PagingBean pagingBean;
    private final RecyclerView recyclerView;
    private MultipleRecyclerAdapter recyclerAdapter;
    private final DataConverter dataConverter;

    public RefreshHandler(SwipeRefreshLayout refreshLayout, PagingBean pagingBean, RecyclerView recyclerView,DataConverter dataConverter) {
        this.refreshLayout = refreshLayout;
        this.pagingBean = pagingBean;
        this.recyclerView = recyclerView;
        this.recyclerAdapter = recyclerAdapter;
        this.dataConverter = dataConverter;
        refreshLayout.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout,new PagingBean(), recyclerView, converter);
    }


    private void refresh(){
        refreshLayout.setRefreshing(true);
        Mamoon.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
                refreshLayout.setRefreshing(false);
            }
        },1000);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void firstPage(String url){
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        MammonLogger.json("mydata",response);
                        final JSONObject jsonObject = JSON.parseObject(response);
                        pagingBean.total = jsonObject.getInteger("total");
                        pagingBean.pageSize = jsonObject.getInteger("page_size");
                        recyclerAdapter = MultipleRecyclerAdapter.create(dataConverter.setJson(response));
                        recyclerAdapter.setOnLoadMoreListener(RefreshHandler.this,recyclerView);
                        recyclerView.setAdapter(recyclerAdapter);
                        pagingBean.addIndex();
                    }
                })
                .build()
                .get();
    }

    private void paging(final String url){
        final int pageSize = pagingBean.pageSize;
        final int currentCount = pagingBean.currentCount;
        final int total = pagingBean.total;
        final int index = pagingBean.pageSize;

        if(recyclerAdapter.getData().size() < pageSize || currentCount >= total){
            recyclerAdapter.loadMoreEnd(true);
        }else {
            Mamoon.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(url+index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                        MammonLogger.json("mydata",response);
                                        dataConverter.clear();
                                        recyclerAdapter.addData(dataConverter.setJson(response).convert());
                                        //累加数量
                                        pagingBean.currentCount = recyclerAdapter.getData().size();
                                        recyclerAdapter.loadMoreComplete();
                                        pagingBean.addIndex();
                                }
                            })
                            .build()
                            .get();
                }
            },1000);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }
}
