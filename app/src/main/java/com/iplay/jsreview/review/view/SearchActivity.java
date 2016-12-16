package com.iplay.jsreview.review.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolBar();
        showOrHideToolBarNavigation(true);
        //setStatusBarCompat();
    }

    @Override
    public String returnToolBarTitle() {
        return "搜索";
    }
}
