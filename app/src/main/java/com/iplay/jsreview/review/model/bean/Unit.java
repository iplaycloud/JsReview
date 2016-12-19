package com.iplay.jsreview.review.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Author : iplay on 2016/1/17.
 * Mail：iplaycloud@gmail.com
 * Description：单元
 */
public class Unit extends BmobObject {

    //单元名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
