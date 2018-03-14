package com.ray.ray_core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.ray.ray_core.R;

import java.util.ArrayList;

/**
 * Created by wrf on 2018/3/12.
 */

public class StarLayout extends LinearLayout implements View.OnClickListener {

    private static final CharSequence ICON_UN_SELECTED = "{fa-star-o}";
    private static final CharSequence ICON_SELECTED = "{fa-star}";
    private static final int STAR_COUNT = 5;
    private static final ArrayList<IconTextView> STARS = new ArrayList<>();

    public StarLayout(Context context) {
        this(context,null);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        for (int i = 0; i < STAR_COUNT; i++) {
            IconTextView iconTextView = new IconTextView(getContext());
            iconTextView.setGravity(Gravity.CENTER);
            LayoutParams lp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT
            );
            lp.weight = 1;
            iconTextView.setLayoutParams(lp);
            iconTextView.setText(ICON_UN_SELECTED);
            iconTextView.setTag(R.id.star_index,i);
            iconTextView.setTag(R.id.star_is_selected,false);
            iconTextView.setOnClickListener(this);

            STARS.add(iconTextView);
            addView(iconTextView);
        }
    }

    @Override
    public void onClick(View v) {
        final IconTextView iconTextView = (IconTextView) v;
        int index = (int) iconTextView.getTag(R.id.star_index);
        boolean isSelected = (boolean) iconTextView.getTag(R.id.star_is_selected);
        if(isSelected){
            unSelectStar(index);
        }else {
            selectStar(index);
        }
    }

    private void unSelectStar(int index) {
        for (int i = index; i < STAR_COUNT; i++) {
            IconTextView iconTextView = STARS.get(i);
            iconTextView.setText(ICON_UN_SELECTED);
            iconTextView.setTag(R.id.star_is_selected,false);
        }
    }

    private void selectStar(int index) {
        for (int i = 0; i <= index; i++) {
            IconTextView iconTextView = STARS.get(i);
            iconTextView.setText(ICON_SELECTED);
            iconTextView.setTag(R.id.star_is_selected,true);
        }
    }

    public int getStarCount(){
        int total = 0;

        for (IconTextView star : STARS) {
            boolean isSelected = (boolean) star.getTag(R.id.star_is_selected);
            if(isSelected){
                total += 1;
            }
        }

        return total;
    }
}
