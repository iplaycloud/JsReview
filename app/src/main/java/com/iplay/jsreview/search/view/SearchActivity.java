package com.iplay.jsreview.search.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolBar();
    }

    @Override
    public String returnToolBarTitle() {
        return "搜索";
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.tb_search_toolbar);
        if (toolbar != null) {
//            toolbar.setLogo(R.mipmap.ic_top);
            toolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
            toolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextApperance);
            setSupportActionBar(toolbar);
        }

        toolbar.setNavigationIcon(R.mipmap.ic_top_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
