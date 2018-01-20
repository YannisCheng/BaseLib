package com.yannischeng.baselib.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * BaseObservableSet
 *
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public class BaseObservableSet {
    private static final String TAG = "BaseObservableSet";


    protected <T> Observable<T> myObservable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
