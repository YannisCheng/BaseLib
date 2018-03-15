package com.yannischeng.police.utils;

import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;

/**
 * Created by wenjia Cheng on 2017/10/16.
 * e-mail:cwj1714@163.com
 *
 * 这是用于在JPEG文件或RAW图像文件中读写Exif标签的类。
 * 支持的格式有：JPEG，DNG，CR2，NEF，NRW，ARW，RW2，ORF和RAF。
 * JPEG图像文件支持属性突变。
 *
 * 注：此类仅能获取 Android本地图片的信息。
 */
public class ImageExifUtil {

    private static final String TAG = "ImageExifUtil";
    private static ExifInterface mExifInterface;
    private static ImageExifUtil mImageExifUtil;

    private ImageExifUtil(String path){
        if (mExifInterface == null) {
            try {
                mExifInterface = new ExifInterface(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized ImageExifUtil getMyInstance(String path){
        if (mImageExifUtil == null) {
            mImageExifUtil = new ImageExifUtil(path);
        }
        return mImageExifUtil;
    }

    public int getOrientationTAG(){
        int orientation = 0;
        int oriNum = mExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (oriNum) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                orientation = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                orientation = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                orientation = 270;
                break;
                default:
        }
        Log.e(TAG, "getOrientationTAG: " + orientation );
        return orientation;
    }
    
}
