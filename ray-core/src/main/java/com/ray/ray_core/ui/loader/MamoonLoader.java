package com.ray.ray_core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ray.ray_core.R;
import com.ray.ray_core.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wrf on 2018/1/22.
 */

public class MamoonLoader {

    private static final String LOADER_STYLE_DEFUALT = LoaderType.BallPulseIndicator.name();
    private static final int LOADER_SIZE_SCALE = 8;

    private static final List<AppCompatDialog> LIST_DIALOGS = new ArrayList<>();
    private static final int LOADER_OFFSITE_SCALE = 10;

    public static void showDialog(Context context, String type){
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(context,type);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        Window window = dialog.getWindow();
        if(window!=null){
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHeight/LOADER_SIZE_SCALE;
            lp.height += deviceHeight/LOADER_OFFSITE_SCALE;
            lp.gravity = Gravity.CENTER;
        }

        LIST_DIALOGS.add(dialog);
        dialog.show();
    }

    public static void showDialog(Context context, Enum<LoaderType> type){
        if(type == null){
            showDialog(context);
        }else {
            showDialog(context,type.name());
        }
    }

    public static void showDialog(Context context){
        showDialog(context,LOADER_STYLE_DEFUALT);
    }

    public static void stopDialog(){
        for(AppCompatDialog dialog:LIST_DIALOGS){
            if(dialog!=null && dialog.isShowing()){
                dialog.cancel();
            }
        }
    }

}
