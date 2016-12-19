package com.iplay.jsreview.setting.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Author : iplay on .
 * Mail：iplaycloud@gmail.com
 * Description：
 */
public class Suggest extends BmobObject {
    private String msg;
    private String mail_qq;

    public String getMail_qq() {
        return mail_qq;
    }

    public void setMail_qq(String mail_qq) {
        this.mail_qq = mail_qq;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
