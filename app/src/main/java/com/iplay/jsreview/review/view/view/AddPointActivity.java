package com.iplay.jsreview.review.view.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;

public class AddPointActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);

        initToolBar();
        showOrHideToolBarNavigation(true);
    }

    @Override
    public String returnToolBarTitle() {
        return "添加内容";
    }
}
