package com.yannischeng.police.ui.map_baidu;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.yannischeng.guolvtj.R;
import com.yannischeng.police.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * BaiduMapActivity  百度地图
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/3/15
 * <p>
 * <p>
 * 创建步骤：
 * BaiduMap
 * LocationClient
 * LocationClientOption
 */
public class BaiduMapActivity extends BaseActivity {

    private static final String TAG = "BaiduMapActivity";

    @BindView(R.id.bmapView)
    MapView mBmapView;
    @BindView(R.id.show_msg)
    TextView mShowMsg;
    @BindView(R.id.btn_location)
    Button mBtnLocation;
    @BindView(R.id.show_msg_current)
    TextView mShowMsgCurrent;

    private BaiduMap mBaiduMap = null;

    /**
     * 定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
     */
    private LocationClient mLocationClient = null;

    /**
     * 配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
     */
    private LocationClientOption mClientOption = null;
    private YcSensorEventListener myOrientationListener;

    /**
     * 自定义图标
     */
    private BitmapDescriptor mDescriptor;

    /**
     * 定位图层
     */
    private MyLocationConfiguration.LocationMode mLocationMode;
    /**
     * 地理位置接口实现
     */
    private YcBdBslListener locationListener;

    /**
     * 此处的经纬度信息只是为了 在外部供用户手动定位当前位置使用
     */
    private double mLatitude;
    private double mLongitude;

    /**
     * mCurrentX值只是为了更改定位图标的角度
     */
    private float mCurrentX;

    /**
     * 精度
     */
    private float mCurrentAccracy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext,注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_baidu_map);
        ButterKnife.bind(this);
        doMap();
    }

    @Override
    public void netError() {

    }

    @Override
    public void netOK() {

    }

    private void doMap() {
        // 获取 mBaiduMap 对象
        mBaiduMap = mBmapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        // 百度定位核心类
        mLocationClient = new LocationClient(getApplicationContext());
        // 定位参数设置
        setLocationOptions();

        myOrientationListener = new YcSensorEventListener(BaiduMapActivity.this);

        myOrientationListener.setOnOrientationListener(new YcSensorEventListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });

        // 设置初始化比例尺
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15.0f);
        mDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_local_blue);
        mBaiduMap.setMapStatus(mapStatusUpdate);

        // 位置模式（多个） - 普通定位样式
        mLocationMode = MyLocationConfiguration.LocationMode.FOLLOWING;

        // 初始化地理位置监听接口的实现
        locationListener = new YcBdBslListener();

        // 注册 定位监听结果接口
        mLocationClient.registerLocationListener(locationListener);
    }

    private void setLocationOptions() {
        // 定位参数设置 http://wiki.lbsyun.baidu.com/cms/androidloc/doc/v7.0/index.html
        mClientOption = new LocationClientOption();
        // 打开gps
        mClientOption.setOpenGps(true);
        // 设置扫描间隔
        mClientOption.setScanSpan(1000);
        // 网络定位下显示方向
        mClientOption.setNeedDeviceDirect(true);
        // 3中精度模式 此处为高精度模式
        mClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 需要返回位置POI信息，在BDLocation.getPoiList()中得到数据
        mClientOption.setIsNeedLocationPoiList(true);
        // 设置海拔高度信息，位置语义化信息
        mClientOption.setIsNeedLocationDescribe(true);
        // 设置海拔高度信息
        mClientOption.setIsNeedAltitude(true);
        // 设置返回地址信息
        mClientOption.setIsNeedAddress(true);
        // 设置坐标类型,取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
        mClientOption.setCoorType("bd09ll");
        // 将 定位参数 添加到 核心定位类中
        mLocationClient.setLocOption(mClientOption);
        mLocationClient.requestLocation();
    }

    @OnClick(R.id.btn_location)
    public void onViewClicked() {
        getCurrentLocation();
    }

    /**
     * 获取当前的定位位置
     * <p>
     * 此方法应该在用户需要的时候调用
     */
    private void getCurrentLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        mShowMsgCurrent.setText("纬度：" + latLng.latitude + "，经度：" + latLng.longitude);
        Log.e(TAG, "ヽ(｀Д´)ﾉ -> getCurrentLocation : " + latLng.toString());
        MapStatusUpdate mapStatusUpdate1 = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(mapStatusUpdate1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效
        mBaiduMap.setMyLocationEnabled(true);

        if (!mLocationClient.isStarted()) {
            // 开始定位
            mLocationClient.start();
        }

        myOrientationListener.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        myOrientationListener.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBmapView.onDestroy();
    }

    /**
     * 定位请求回调
     */
    class YcBdBslListener implements BDLocationListener {

        private static final String TAG = "YcBdBslListener";

        /**
         * 初次定位
         */
        boolean isFirstLocation = true;

        /**
         * 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
         * @param bdLocation .
         */
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            /*
            * BDLocation 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
            * MyLocationData 定位数据,定位数据建造器
            * 可以通过BDLocation配置如下参数
            * 1.accuracy 定位精度
            * 2.latitude 百度纬度坐标
            * 3.longitude 百度经度坐标
            * 4.satellitesNum GPS定位时卫星数目 getSatelliteNumber() gps定位结果时，获取gps锁定用的卫星数
            * 5.speed GPS定位时速度 getSpeed()获取速度，仅gps定位结果时有速度信息，单位公里/小时，默认值0.0f
            * 6.direction GPS定位时方向角度
            */
            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();
            mCurrentAccracy = bdLocation.getRadius();

            MyLocationData locationData = new MyLocationData.Builder()
                    //百度经度坐标
                    .longitude(mLongitude)
                    //百度纬度坐标
                    .latitude(mLatitude)
                    //设定图标方向
                    .direction(mCurrentX)
                    //getRadius 获取定位精度,默认值0.0f
                    .accuracy(bdLocation.getRadius())
                    .build();

            //设置定位数据, 只有先允许定位图层后设置数据才会生效，参见 setMyLocationEnabled(boolean)
            // 将获取到的数据在地图上进行显示
            mBaiduMap.setMyLocationData(locationData);


            /*
            * 配置定位图层显示方式,三个参数的构造器:
            * 1.定位图层显示模式
            * 2.是否允许显示方向信息
            * 3.用户自定义定位图标
            */
            MyLocationConfiguration configuration = new MyLocationConfiguration(mLocationMode, true, mDescriptor);
            mBaiduMap.setMyLocationConfigeration(configuration);

            // 初次进入本界面时，自动进行地理位置的更新。
            if (isFirstLocation) {
                //地理坐标基本数据结构
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                //设置缩放中心点；缩放比例；
                builder.target(latLng).zoom(18.0f);
                //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                //改变地图状态
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //给地图设置状态
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                isFirstLocation = false;
            }

            getBaseInfo(bdLocation);

        }

        private void getBaseInfo(BDLocation bdLocation) {
            StringBuffer stringBuffer2 = new StringBuffer();

            stringBuffer2
                    .append("\ngetAddress()  : ")
                    .append(bdLocation.getAddress())
                    .append("\ngetTime() : ")
                    // server返回的当前定位时间
                    .append(bdLocation.getTime())
                    .append("\ngetAddrStr(): ")
                    // 获取详细地址信息
                    .append(bdLocation.getAddrStr())
                    .append("\ngetCountry() : ")
                    .append(bdLocation.getCountry())
                    .append("\ngetCountryCode() : ")
                    .append(bdLocation.getCountryCode())
                    .append("\ngetProvince() : ")
                    // 获取省份
                    .append(bdLocation.getProvince())
                    // 获取城市名
                    .append("\ngetCity() : ")
                    .append(bdLocation.getCity())
                    .append("\ngetCityCode() : ")
                    .append(bdLocation.getCityCode())
                    .append("\ngetDistrict() : ")
                    // 获取区/县信息
                    .append(bdLocation.getDistrict())
                    .append("\ngetStreet() : ")
                    // 获取街道信息
                    .append(bdLocation.getStreet())
                    .append("\ngetStreetNumber() : ")
                    // 获取街道号码
                    .append(bdLocation.getStreetNumber())
                    .append("\ngetLocationDescribe() : ")
                    //获取位置语义化信息，没有的话返回NULL
                    .append(bdLocation.getLocationDescribe())
                    .append("\ngetLongitude() : ")
                    // 获取经度坐标
                    .append(bdLocation.getLongitude())
                    .append("\ngetLatitude() : ")
                    // 获取纬度坐标
                    .append(bdLocation.getLatitude());
            for (Poi item : bdLocation.getPoiList()) {
                Log.e(TAG, "ヽ(｀Д´)ﾉ -> getBaseInfo getId : " + item.getId() +
                        ", \n getName: " + item.getName() +
                        ", \n getRank: " + item.getRank() +
                        ",\n describeContents :" + item.describeContents());
                mShowMsg.setText(stringBuffer2);
            }
        }
    }
}
