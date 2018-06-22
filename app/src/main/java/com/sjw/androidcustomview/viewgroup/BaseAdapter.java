package com.sjw.androidcustomview.viewgroup;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pc on 2018/6/20.
 */

public abstract class BaseAdapter {

//    1.知道有多少个条目
    public abstract int getCount();

    //2.getView通过position
    public abstract View getView(int position, ViewGroup parent);

    //观察者模式通知及时更新


}
