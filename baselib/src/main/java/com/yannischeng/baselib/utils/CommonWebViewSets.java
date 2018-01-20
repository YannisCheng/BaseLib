package com.yannischeng.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * CommonWebViewSets  WebView设置工具类
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/12/29
 */
public class CommonWebViewSets {

    /**
     * 设置URL
     *
     * @param webView 控件
     * @param url     url地址
     */
    public static void setUrl(WebView webView, String url, ProgressBar mProgressBar) {
        initWeb(webView, mProgressBar);
        webView.loadUrl(url);
    }

    /**
     * 设置html源代码，无base Url
     *
     * @param webView 控件
     * @param html    html源代码
     */
    public static void setHtmlCode(WebView webView, String html, ProgressBar mProgressBar) {
        initWeb(webView, mProgressBar);
        webView.loadDataWithBaseURL("我的服务商", html, "text/html", "utf-8", null);
    }

    /**
     * 设置html源代码，有base Url
     *
     * @param webView 控件
     * @param baseUrl baseUrl
     * @param html    html源代码
     */
    public static void setHtmlCodeBaseUrl(WebView webView, String baseUrl, String html, ProgressBar mProgressBar) {
        initWeb(webView, mProgressBar);
        webView.loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", null);
    }

    /**
     * @param webView 控件
     */
    @SuppressLint("SetJavaScriptEnabled")
    public static void initWeb(WebView webView, final ProgressBar mProgressBar) {
        // 获取webview设置属性
        WebSettings settings = webView.getSettings();

        // 设置编码格式
        settings.setDefaultTextEncodingName("utf-8");
        // 进行屏幕适配设置
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // 设置支持JavaScript
        settings.setJavaScriptEnabled(true);
        // 这个是给图片设置点击监听的，如果你项目需要webview中图片，点击查看大图功能，可以这么添加
        // webView.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");

        settings.setAllowContentAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setAllowFileAccess(false);
        // 把html中的内容放大webview等宽的一列中
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //settings.setDefaultFontSize(44);
        // 设置可以支持缩放
        //webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        //webView.getSettings().setBuiltInZoomControls(true);
        //webView.getSettings().setDisplayZoomControls(true);

        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                /*if (url.toString().contains("sina.cn")){
                    view.loadUrl("http://ask.csdn.net/questions/178242");
                    return true;
                }*/
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl().toString().contains("sina.cn")){
                        view.loadUrl("http://ask.csdn.net/questions/178242");
                        return true;
                    }
                }*/
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //imgReset(view);//重置webview中img标签的图片大小
                // html加载完成之后，添加监听图片的点击js函数
                //addImageClickListner(view);
            }


        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressBar.setProgress(newProgress);//设置进度值
                }

            }
        });
    }


    /**
     * 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
     *
     * @param view view
     */
    private static void addImageClickListner(WebView view) {
        view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private static void imgReset(WebView view) {
        view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    private static class JavaScriptInterface {
        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            Log.i("TAG", "响应点击事件!");
            Intent intent = new Intent();
            intent.putExtra("image", img);
            // BigImageActivity查看大图的类，自己定义就好
            // intent.setClass(context, BigImageActivity.class);
            context.startActivity(intent);
        }
    }
}
