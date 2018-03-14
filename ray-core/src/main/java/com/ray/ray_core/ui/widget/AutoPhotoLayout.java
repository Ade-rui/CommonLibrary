package com.ray.ray_core.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;
import com.ray.ray_core.R;
import com.ray.ray_core.delegates.MammonDelegate;
import com.ray.ray_core.util.log.MammonLogger;

import java.util.ArrayList;

/**
 * Created by wrf on 2018/3/13.
 */

public class AutoPhotoLayout extends LinearLayout {

    private static final String ICON_TEXT = "{fa-plus}";
    private int maxCount;
    private int itemMargin;
    private int lineCount;
    private float iconSize;

    private LayoutParams iconParams;
    private boolean isFirstMeasure = true;
    private ArrayList<View> lineViews;
    private boolean isFirstLayout = true;

    private final ArrayList<ArrayList<View>> LINE_VIEWS = new ArrayList<>();
    private final ArrayList<Integer> LINE_HEIGHTS = new ArrayList<>();

    private IconTextView iconAdd;

    private MammonDelegate delegate;
    private ImageView targetImageView;
    private int currentNum;

    public AutoPhotoLayout(Context context) {
        this(context,null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoPhotoLayout);
        maxCount = typedArray.getInt(R.styleable.AutoPhotoLayout_max_count,1);
        itemMargin = typedArray.getInt(R.styleable.AutoPhotoLayout_item_margin,0);
        lineCount = typedArray.getInt(R.styleable.AutoPhotoLayout_line_count,3);
        iconSize = typedArray.getDimension(R.styleable.AutoPhotoLayout_icon_size,20);
        typedArray.recycle();
    }

    public void setDelegate(MammonDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //最终宽高
        int width = 0;
        int height = 0;

        //每一行行宽和高
        int lineWidth = 0;
        int lineHeight = 0;

        //得到子元素个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //测量子view的宽和高
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //margin
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            //子view占据的宽度
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            //子view占据的高度
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            //换行
            if(lineWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()){
                width = Math.max(lineWidth,width);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            }else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }
            if(i == childCount - 1){
                width = Math.max(lineWidth,width);
                //因为每一次换行 只是加了上一行的高度，如果最后一个view没有换行，那么就不会执行累加高度，所以要在这对最后一个view做处理，如果是最后一个view切没换行，就累加
                height += lineHeight;
            }

            setMeasuredDimension(
                    widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingLeft() + getPaddingRight(),
                    heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom()
            );

            //设置一行所有图片的高度
            final int imageSize = widthSize / lineCount;
            if(isFirstMeasure){
                iconParams = new LayoutParams(imageSize,imageSize);
                isFirstMeasure = false;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LINE_VIEWS.clear();
        LINE_HEIGHTS.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        if(isFirstLayout){
            lineViews = new ArrayList<>();
            isFirstLayout = false;
        }

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            //如果需要换行
            if(lineWidth + childWidth + layoutParams.leftMargin + layoutParams.rightMargin > width - getPaddingLeft() + getPaddingRight()){
                //记录lineHeight
                LINE_HEIGHTS.add(lineHeight);
                //记录当前一行的view
                LINE_VIEWS.add(lineViews);
                //重置宽和高
                lineWidth = 0;
                lineHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                lineViews.clear();
            }
            lineWidth += childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            lineHeight = Math.max(lineHeight,lineHeight + layoutParams.topMargin + layoutParams.bottomMargin);
            lineViews.add(child);
        }

        //处理最后一行
        LINE_HEIGHTS.add(lineHeight);
        LINE_VIEWS.add(lineViews);
        //设置子view位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        final int lineNum = LINE_VIEWS.size();
        for (int i = 0; i < lineNum; i++) {
            //当前所有的view
            lineViews = LINE_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            int size = lineViews.size();
            for (int y = 0; y < size; y++) {
                View child = lineViews.get(y);
                //判断child的状态
                if(child.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                //设置子view的边距
                final int lc = left + layoutParams.leftMargin;
                final int tc = top + layoutParams.topMargin;
                final int rc = lc + child.getMeasuredWidth() ;
                final int bc = tc + child.getMeasuredHeight();
                //为子view布局
                child.layout(lc,tc,rc,bc);
                left += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }

        iconAdd.setLayoutParams(iconParams);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initIconAdd();
    }

    private void initIconAdd() {
        iconAdd = new IconTextView(getContext());
        iconAdd.setText(ICON_TEXT);
        iconAdd.setGravity(Gravity.CENTER);
        iconAdd.setTextSize(iconSize);
        iconAdd.setBackgroundResource(R.drawable.border_text);
        iconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.startCameraWithCheck();
            }
        });
        addView(iconAdd);
    }

    public void onCropTarget(Uri uri){
        createNewImageView();
        Glide.with(delegate)
                .load(uri)
                .into(targetImageView);
    }

    private void createNewImageView() {
        targetImageView = new ImageView(getContext());
        targetImageView.setId(currentNum);
        targetImageView.setLayoutParams(iconParams);
        //添加子view的时候传入位置
        addView(targetImageView,currentNum);
        currentNum++;
        if(currentNum >= maxCount){
            iconAdd.setVisibility(View.GONE);
        }
    }
}
