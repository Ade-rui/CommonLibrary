package com.ray.ray_core.ui.date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import freemarker.template.SimpleDate;

/**
 * Created by wrf on 2018/3/10.
 */

public class DateUtil {

    public interface OnDateChoiceListener{
        void onChoice(Date date,String dateStr);
    }

    public static void show(Context context, final OnDateChoiceListener listener){
//        LinearLayout container = new LinearLayout(context);
//        DatePicker picker = new DatePicker(context);
//        final LinearLayout.LayoutParams lp =
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//        container.setLayoutParams(lp);
//
//        picker.init(1990, 1, 1, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar instance = Calendar.getInstance();
//                instance.set(year,monthOfYear,dayOfMonth);
//                Date time = instance.getTime();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd日");
//                String formatDate = format.format(time);
//                if(listener!=null){
//                    listener.onChoice(time,formatDate);;
//                }
//            }
//        });
//
//        container.addView(picker);
//
//        new AlertDialog.Builder(context)
//                .setView(container)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .show();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar instance = Calendar.getInstance();
                instance.set(year,monthOfYear,dayOfMonth);
                Date time = instance.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                String formatDate = format.format(time);
                if(listener!=null){
                    listener.onChoice(time,formatDate);;
                }
            }
        },1990,0,1);

        datePickerDialog.show();
    }

}
