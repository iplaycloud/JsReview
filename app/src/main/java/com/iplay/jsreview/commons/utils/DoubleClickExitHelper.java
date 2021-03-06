package com.iplay.jsreview.commons.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.system.AppManager;


/**
 * Author : iplay on 2015/7/21 15:30
 * Mail：iplaycloud@gmail.com
 * Description：双击退出
 */
public class DoubleClickExitHelper {
    private final Activity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;
    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if (mBackToast != null) {
                mBackToast.cancel();
            }
        }
    };

    public DoubleClickExitHelper(Activity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Activity onKeyDown事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            // �?�?
            AppManager.getAppManager().AppExit(mActivity);
            return true;
        } else {
            isOnKeyBacking = true;
            if (mBackToast == null) {
                mBackToast = Toast.makeText(mActivity, R.string.tip_double_click_exit, Toast.LENGTH_SHORT);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }
}
