package com.ray.ray_core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.ray.ray_core.R;
import com.ray.ray_core.R2;
import com.ray.ray_core.delegates.MammonDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by wrf on 2018/2/1.
 */

public abstract class BaseBottomDelegate extends MammonDelegate implements View.OnClickListener {

    private final ArrayList<BottomTabBean> LIST_TABS = new ArrayList<>();
    private final ArrayList<BottomItemDelegate> LIST_ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> MAP_ITEMS = new LinkedHashMap<>();
    private int mIndexDelegate = 0;
    private int mCurrentIndex;
    private int mClickedColor = Color.BLUE;

    @BindView(R2.id.container_lv_bottom)
    LinearLayoutCompat containerBottom;

    protected abstract  LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(ItemBuilder itemBuilder);

    /**
     * 设置要显示的delegate索引
     * @return
     */
    protected abstract int setDelegateIndex();

    /*
    * 点击的颜色
    * */
    protected abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndexDelegate = setDelegateIndex();
        if(setClickedColor() != 0){
            mClickedColor = setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);

        MAP_ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : MAP_ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            LIST_TABS.add(key);
            LIST_ITEM_DELEGATES.add(value);
        }

    }



    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = MAP_ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_item_icon_text, containerBottom);
            final RelativeLayout bottom_item = (RelativeLayout) containerBottom.getChildAt(i);

            bottom_item.setTag(i);
            bottom_item.setOnClickListener(this);

            IconTextView icon = (IconTextView) bottom_item.findViewById(R.id.bottom_item_icon);
            AppCompatTextView tvTitle = (AppCompatTextView) bottom_item.findViewById(R.id.bottom_item_title);

            BottomTabBean bottomTabBean = LIST_TABS.get(i);
            icon.setText(bottomTabBean.getICON());
            tvTitle.setText(bottomTabBean.getTITLE());
            if(i == mIndexDelegate){
                icon.setTextColor(mClickedColor);
                tvTitle.setTextColor(mClickedColor);
                mCurrentIndex = i;
            }
        }

        final ISupportFragment[] supportFragments = LIST_ITEM_DELEGATES.toArray(new SupportFragment[LIST_ITEM_DELEGATES.size()]);
        loadMultipleRootFragment(R.id.container_layout_content,mCurrentIndex,supportFragments);
    }

    private void resetColor(){
        int childCount = containerBottom.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RelativeLayout item = (RelativeLayout) containerBottom.getChildAt(i);
            setColor(item,Color.GRAY);
        }
    }


    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if(v instanceof RelativeLayout){
            RelativeLayout item = (RelativeLayout) v;
            resetColor();
            setColor(item,mClickedColor);
        }

        getSupportDelegate().showHideFragment(LIST_ITEM_DELEGATES.get(tag),LIST_ITEM_DELEGATES.get(mCurrentIndex));
        mCurrentIndex = tag;
    }

    private void setColor(RelativeLayout item,int color){
        IconTextView icon = (IconTextView) item.getChildAt(0);
        AppCompatTextView tvTitle = (AppCompatTextView) item.getChildAt(1);
        icon.setTextColor(color);
        tvTitle.setTextColor(color);
    }
}
