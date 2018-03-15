package com.yannischeng.police.ui.map_baidu;

import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * YcBdBslListener 实现百度地图 位置信息接口
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/12/1
 */
public class YcBdBslListener implements BDLocationListener {

    private static final String TAG = "YcBdBslListener";

    /**
     * 初次定位
     */
    boolean isFirstLocation = true;
    /**
     * 此处的经纬度信息只是为了 在外部供用户手动定位当前位置使用
     */
    double mLatitude;
    double mLongitude;
    float mCurrentX;
    float mCurrentAccracy;
    BaiduMap mBaiduMap;
    MyLocationConfiguration.LocationMode mLocationMode;
    BitmapDescriptor mIconLocation;
    TextView mTextView = null;


    public YcBdBslListener(float currentX, BaiduMap baiduMap, MyLocationConfiguration.LocationMode locationMode, BitmapDescriptor iconLocation, TextView textView) {
        mCurrentX = currentX;
        mBaiduMap = baiduMap;
        mLocationMode = locationMode;
        mIconLocation = iconLocation;
        mTextView = textView;

    }

    public YcBdBslListener(float currentX, BaiduMap baiduMap, MyLocationConfiguration.LocationMode locationMode,TextView textView) {
        mCurrentX = currentX;
        mBaiduMap = baiduMap;
        mLocationMode = locationMode;
        mTextView = textView;

    }

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
        MyLocationConfiguration configuration = new MyLocationConfiguration(mLocationMode, true, mIconLocation);
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
        // http://wiki.lbsyun.baidu.com/cms/androidloc/doc/v7.0/index.html
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();

        stringBuffer
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
                .append(bdLocation.getLatitude())
                .append("\ngetBuildingID() : ")
                .append(bdLocation.getBuildingID())
                .append("\ngetBuildingID() : ")
                .append(bdLocation.getBuildingID())
                .append("\ngetCoorType() : ")
                .append(bdLocation.getCoorType())
                .append("\ngetDirection() : ")
                // gps定位结果时，行进的方向，单位度
                .append(bdLocation.getDirection())
                .append("\ngetFloor() : ")
                // 获取楼层信息，目前只在百度支持室内定位的地方有返回，默认null
                .append(bdLocation.getFloor())
                .append("\ngetIndoorNetworkState() : ")
                // 返回室内定位网络状态 #INDOOR_NETWORK_STATE_HIGH, #INDOOR_NETWORK_STATE_LOW, #INDOOR_NETWORK_STATE_MIDDLE
                .append("\ngetLocationWhere() : ")
                // 获取当前定位是国内还是国外
                .append(bdLocation.getLocationWhere())
                .append("\ngetLocType() : ")
                // 获取定位类型: 参考 定位结果描述 相关的字段
                .append(bdLocation.getLocType())
                .append("\ngetLocTypeDescription() : ")
                // 获取定位类型相关描述信息
                .append("\ngetNetworkLocationType() : ")
                // 在网络定位结果的情况下，获取网络定位结果是通过基站定位得到的还是通过wifi定位得到的还是GPS得结果
                .append(bdLocation.getNetworkLocationType())
                .append("\ngetOperators() : ")
                // 获取运营商信息
                .append(bdLocation.getOperators())
                .append("\ngetPoiList() : ")
                // 仅在开发者设置需要POI信息时才会返回，在网络不通或无法获取时有可能返回null
                .append(bdLocation.getPoiList())
                .append("\ngetRadius() : ")
                // 获取定位精度,默认值0.0f
                .append(bdLocation.getRadius())
                .append("\ngetSpeed() : ")
                // 获取速度，仅gps定位结果时有速度信息，单位公里/小时，默认值0.0f
                .append(bdLocation.getSpeed())
                .append("\ngetAltitude(): ")
                // 获取高度信息，目前只有是GPS定位结果时或者设置LocationClientOption.setIsNeedAltitude(true)时才有效，单位米
                .append(bdLocation.getAltitude())
                .append("\ngetUserIndoorState() : ")
                // 返回用户室内外状态，#USER_INDDOR_TRUE,#USER_INDOOR_FALSE,#USER_INDOOR_UNKNOW
                .append("\nhasAddr() : ")
                // 是否有地址信息
                .append(bdLocation.hasAddr())
                .append("\nhasAltitude() : ")
                // 是否包含高度信息
                .append(bdLocation.hasAltitude())
                .append("\nhasRadius() : ")
                // 是否包含半径信息
                .append(bdLocation.hasRadius())
                .append("\nhasSpeed() : ")
                // 是否包含速度信息
                .append(bdLocation.hasSpeed())
                .append("\nisIndoorLocMode() : ")
                // 是否处于室内定位模式
                .append(bdLocation.isIndoorLocMode());


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
            Log.e(TAG, "ヽ(｀Д´)ﾉ -> getBaseInfo getId : " + item.getId() + ", getName: " + item.getName() + ", getRank: " + item.getRank() + ", describeContents :" + item.describeContents());
            mTextView.setText(stringBuffer2);
        }




        Log.e(TAG, "ヽ(｀Д´)ﾉ -> location: " + stringBuffer);

        /**
         ヽ(｀Д´)ﾉ -> location:
         getAddress()  : com.baidu.location.Address@a47419f
         getTime() : 2017-12-02 11:10:06
         getAddrStr(): 中国天津市南开区自贡道56号
         getCountry() : 中国
         getCountryCode() : 0
         getProvince() : 天津市
         getCity() : 天津市
         getCityCode() : 332
         getDistrict() : 南开区
         getStreet() : 自贡道
         getStreetNumber() : 56号
         getLocationDescribe() : 在金川里附近
         getLongitude() : 117.138207
         getLatitude() : 39.132626
         getBuildingID() : null
         getBuildingID() : null
         getCoorType() : bd09ll
         getDirection() : 69.0
         getFloor() : null
         getIndoorNetworkState() : 0
         getLocationWhere() : 1
         getLocType() : 161
         getLocTypeDescription() : NetWork location successful!
         getNetworkLocationType() : wf
         getOperators() : 0
         getPoiList() : [com.baidu.location.Poi@64f92ec, com.baidu.location.Poi@d2da6b5, com.baidu.location.Poi@a39514a, com.baidu.location.Poi@a6858bb, com.baidu.location.Poi@518f1d8]
         getRadius() : 40.0
         getSpeed() : 0.0
         getAltitude(): 1.7
         getUserIndoorState() : 1
         hasAddr() : true
         hasAltitude() : true
         hasRadius() : true
         hasSpeed() : false
         isIndoorLocMode() : false
         */
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public float getCurrentX() {
        return mCurrentX;
    }

    public float getCurrentAccracy() {
        return mCurrentAccracy;
    }
}
