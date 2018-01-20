package com.yannischeng.baselib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;

/**
 * PermissionUtil 在Activity中使用的权限工具
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/12/2
 */
public class PermissionUtil {

    /**
     * 9个权限组
     */
    //1- 通讯录
    public static final int CODE_PERMISSION_CONTACTS = 0;
    //2 - 手机信息
    public static final int CODE_PERMISSION_PHONE = 1;
    //3 - 日历
    public static final int CODE_PERMISSION_CALENDAR = 2;
    //4 - 相机
    public static final int CODE_PERMISSION_CAMERA = 3;
    //5 - 传感器
    public static final int CODE_PERMISSION_SENSORS = 4;
    //6 - 定位
    public static final int CODE_PERMISSION_LOCATION = 5;
    //7 - 存储
    public static final int CODE_PERMISSION_STORAGE = 6;
    //8 - 麦克风
    public static final int CODE_PERMISSION_MICROPHONE = 7;
    //9 - 信息
    public static final int CODE_PERMISSION_SMS = 8;

    /**
     * 每组只要有一个权限申请成功了，就默认整组权限都可以使用了。
     */

    public static final String STR_PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String STR_PERMISSION_PHONE = Manifest.permission.CALL_PHONE;
    public static final String STR_PERMISSION_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String STR_PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String STR_PERMISSION_SENSORS = Manifest.permission.BODY_SENSORS;
    public static final String STR_PERMISSION_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String STR_PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String STR_PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String STR_PERMISSION_MICROPHONE = Manifest.permission.RECORD_AUDIO;
    public static final String STR_PERMISSION_SMS = Manifest.permission.READ_SMS;

    public static String[] requestPermissions = {
            STR_PERMISSION_CONTACTS,
            STR_PERMISSION_PHONE,
            STR_PERMISSION_CALENDAR,
            STR_PERMISSION_CAMERA,
            STR_PERMISSION_SENSORS,
            STR_PERMISSION_LOCATION,
            STR_PERMISSION_STORAGE,
            STR_PERMISSION_MICROPHONE,
            STR_PERMISSION_SMS
    };


    public static void showDialog(final Activity activity, final String[] requestPermission, final int requestCode) {
        showMessageOKCancel(activity, "若不能授予此项权限，此功能则不能正常使用", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 授权
                dialog.dismiss();
                activity.requestPermissions(requestPermission, requestCode);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取消
                dialog.dismiss();
                activity.finish();
            }
        });
    }

    /**
     * 执行弹窗
     */
    public static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListerer) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("授权", okListener)
                .setNegativeButton("取消", noListerer)
                .create()
                .show();
    }

}
