
package com.iplay.jsreview.setting.view;

import android.os.Bundle;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;

public class AuthorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        initToolBar();
        showOrHideToolBarNavigation(true);
        setStatusBarCompat();
    }

    @Override
    public String returnToolBarTitle() {
        return getString(R.string.author);

    }
}
