package com.iplay.jsreview.setting.model.bean;

/**
 * Author : iplay on .
 * Mail：iplaycloud@gmail.com
 * Description：记录更新日志
 */
public class VersionNote {

    private String versionName;
    private String updateTime;
    private String updateNote;

    public VersionNote(String versionName, String updateTime, String updateNote) {
        this.versionName = versionName;
        this.updateTime = updateTime;
        this.updateNote = updateNote;
    }

    public String getUpdateNote() {
        return updateNote;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getUpdateTime() {
        return updateTime;
    }


}
