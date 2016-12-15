

package com.iplay.jsreview.commons.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Author : iplay on 2016/1/17.
 * Mail：iplaycloud@gmail.com
 * Description：
 */
public class ToastHelper {

    public static void showString(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
