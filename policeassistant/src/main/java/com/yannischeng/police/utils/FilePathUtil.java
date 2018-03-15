package com.yannischeng.police.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by wenjia Cheng on 2017/10/9.
 * e-mail:cwj1714@163.com
 *
 * 工具类 -- 获取各种文件路径
 */
public class FilePathUtil {

    //获取SD卡各种状态
    private boolean isExternalStorageAvailable = false;
    private boolean isExternalStorageWriteable = false;
    private Context mContext;

    //获取存储空间
    private long freeSpace;
    private long totalSpace;
    private long usableSpace;

    //获取context的存储路径
    private String contextCacheDir = "";
    private String contextExternalCacheDir = "";
    private String contextFileDir = "";
    private String contextObbDir = "";
    private String packageName = "";
    private String apkPath = "";

    //获取SD的存储路径
    private String dataDirectory = "";
    private String externalStorageDirectory = "";
    private String rootDirectory = "";
    private String downloadCacheDirectory = "";


    public FilePathUtil(Context context) {
        mContext = context;
    }

    /**
     * 对SD卡存储状态进行判断 -- 可写
     * 只有 此方法 返回值为 true 才能进行路径的获取
     */
    private boolean SDCardState() {

        String externalStorageState = Environment.getExternalStorageState();

        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            isExternalStorageAvailable = isExternalStorageWriteable = true;
        } else if (externalStorageState.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            isExternalStorageAvailable = true;
            isExternalStorageWriteable = false;
        } else {
            isExternalStorageWriteable = isExternalStorageAvailable = false;
        }

        handEnvironmentSD(isExternalStorageAvailable, isExternalStorageWriteable);
        return isExternalStorageWriteable;
    }


    /**
     * 根据不同的SD卡状态，执行不同的操作
     */
    private void handEnvironmentSD(boolean isExternalStorageAvailable, boolean isExternalStorageWriteable) {
        if (isExternalStorageAvailable && isExternalStorageWriteable) {

            getEnvironmentPath();
            Log.e(TAG, "handEnvironmentSD SD卡 可进行 读 写 操作");
        } else if (!isExternalStorageWriteable) {
            Log.e(TAG, "handEnvironmentSD: SD卡 存在，无法执行 写 操作");
        } else {
            Log.e(TAG, "handEnvironmentSD: SD卡 不存在");
        }

        getContextPath();
    }

    /**
     * 通过Context获取路径 在 非 Activity中使用方式：Context
     */
    private void getContextPath() {
        //返回通过Context.openOrCreateDatabase 创建的数据库文件
        //Context.getDatabasePath();

        contextCacheDir = mContext.getCacheDir().getAbsolutePath();

        contextExternalCacheDir = mContext.getExternalCacheDir().getAbsolutePath();

        contextFileDir = mContext.getFilesDir().getPath();

        contextObbDir = mContext.getObbDir().getPath();

        packageName = mContext.getPackageName();

        apkPath = mContext.getPackageCodePath();

        //获取该程序的安装包路径       /data/app/com.yannischeng.mediarecorderdemo-xp6tNr8G-BNsi92RhI1B8w==/base.apk
        String appInstallPath = mContext.getPackageResourcePath();
    }

    /**
     * 通过Environment获取路径
     */
    private void getEnvironmentPath() {

        dataDirectory = String.valueOf(Environment.getDataDirectory());

        externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        freeSpace = Environment.getExternalStorageDirectory().getFreeSpace() / 1024 / 1024 / 1024;
        totalSpace = Environment.getExternalStorageDirectory().getTotalSpace() / 1024 / 1024 / 1024;
        usableSpace = Environment.getExternalStorageDirectory().getUsableSpace() / 1024 / 1024 / 1024;
        Log.e(TAG, "getEnvironmentPath freeSpace is : " + freeSpace + "G, totalSpace is : " + totalSpace + "G, usableSpace is : " + usableSpace + "G");

        rootDirectory = String.valueOf(Environment.getRootDirectory());

        downloadCacheDirectory = String.valueOf(Environment.getDownloadCacheDirectory());
    }

    /**
     * @return 返回 剩余 空间
     */
    public long getFreeSpace() {
        return freeSpace;
    }

    /**
     * @return 返回 总共 空间
     */
    public long getTotalSpace() {
        return totalSpace;
    }

    /**
     * @return 返回 可用 空间
     */
    public long getUsableSpace() {
        return usableSpace;
    }

    /**
     * @return 用于获取APP的cache目录       /data/user/0/com.yannischeng.mediarecorderdemo/cache
     */
    public String getContextCacheDir() {
        return contextCacheDir;
    }

    /**
     * @return 用于获取APP的在SD卡中的cache目录     /storage/emulated/0/Android/data/com.yannischeng.mediarecorderdemo/cache
     */
    public String getContextExternalCacheDir() {
        return contextExternalCacheDir;
    }

    /**
     * @return 用于获取APP的files目录       /data/user/0/com.yannischeng.mediarecorderdemo/files
     */
    public String getContextFileDir() {
        return contextFileDir;
    }

    /**
     * @return 用于获取APP的SD卡中的obb目录     /storage/emulated/0/Android/obb/com.yannischeng.mediarecorderdemo
     */
    public String getContextObbDir() {
        return contextObbDir;
    }

    /**
     * @return 用于获取APP的所在包目录     /com.yannischeng.mediarecorderdemo
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @return 来获得当前应用程序对应的 apk 文件的路径        /data/app/com.yannischeng.mediarecorderdemo-xp6tNr8G-BNsi92RhI1B8w==/base.apk
     */
    public String getApkPath() {
        return apkPath;
    }

    /**
     * @return 获得根目录          /data 内部存储路径
     */
    public String getDataDirectory() {
        return dataDirectory;
    }

    /**
     * @return 获得SD卡目录       /mnt/sdcard（获取的是手机外置sd卡的路径）
     */
    public String getExternalStorageDirectory() {
        return externalStorageDirectory;
    }

    /**
     * @return //获得系统目录        /system
     */
    public String getRootDirectory() {
        return rootDirectory;
    }

    /**
     * @return 获得缓存目录        /cache
     */
    public String getDownloadCacheDirectory() {
        return downloadCacheDirectory;
    }
}
