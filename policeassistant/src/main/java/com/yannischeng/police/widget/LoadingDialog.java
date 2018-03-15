package com.yannischeng.police.widget;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yannischeng.guolvtj.R;

/**
 * LoadingDialog  弹窗进度条
 *
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2016.07.17
*/
public class LoadingDialog {

    /**
     * 加载数据对话框
     */
    private static Dialog mLoadingDialog;

    /**
     * 显示 加载 对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static Dialog showLoadingDialog(Activity context, String msg, boolean cancelable) {
        View view = getView(context, msg);
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        view.findViewById(R.id.show_waring).setVisibility(View.GONE);
        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }


    /**
     * 显示 加载 对话框
     */
    public static Dialog showLoadingDialog(Activity context) {
        View view = getView(context, "加载中...");
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        view.findViewById(R.id.show_waring).setVisibility(View.GONE);
        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }


    /**
     * 显示 警告 对话框
     */
    public static Dialog showWaringDialog(Activity context, String msg) {
        View view = getView(context, msg);
        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        view.findViewById(R.id.show_waring).setVisibility(View.VISIBLE);

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }


    /**
     * 关闭 对话框
     */
    public static void cancelLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }

    /**
     * 获取View界面
     * @param context context
     * @param msg 显示信息
     * @return 当前view
     */
    @NonNull
    private static View getView(Activity context, String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(msg);
        return view;
    }
}
