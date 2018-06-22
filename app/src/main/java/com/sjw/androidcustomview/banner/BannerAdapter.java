package com.sjw.androidcustomview.banner;

import android.view.View;

/**
 * Created by pc on 2018/6/21.
 */

public abstract class BannerAdapter {
    /**
     * 1.根据位置获取viewpager中的子view
     */
    public abstract View getView(int position, View convertView);

    /**
     * 5.获取轮播的数量
     */
    public abstract int getCount();

    /**
     * 6.根据位置获取广告位的描述
     */
    public String getBannerDesc(int position) {


        return "";
    }

    ;
}
