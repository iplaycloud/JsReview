package com.iplay.jsreview.test.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;
import com.iplay.jsreview.review.model.bean.Content;
import com.iplay.jsreview.review.model.bean.Point;
import com.iplay.jsreview.review.view.AddContentActivity;
import com.iplay.jsreview.test.model.bean.Test;

import cn.bmob.v3.listener.SaveListener;

public class AddTestActivity extends BaseActivity {

    private View mRootView;

    //防止多次提交数据
    private boolean isPosting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        initToolBar();
        showOrHideToolBarNavigation(true);

        initView();
        initArguments();
    }

    private void initView() {
    }

    public void initArguments() {
    }

    @Override
    public String returnToolBarTitle() {
        return "添加试题";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_content_submit:
                Toast.makeText(this, "submit", Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty("")) {
                    Snackbar.make(mRootView, R.string.dont_no_text, Snackbar.LENGTH_SHORT).show();
                } else {
                    Test mTest = new Test();

                    if (!isPosting) {
                        isPosting = true;
                        mTest.save(AddTestActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                isPosting = false;
                                Toast.makeText(AddTestActivity.this, R.string.submit_success, Toast.LENGTH_SHORT).show();
                                //finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                isPosting = false;
                                Snackbar.make(mRootView, R.string.submit_failed, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Snackbar.make(mRootView, R.string.dont_repeat, Snackbar.LENGTH_SHORT).show();
                    }
                }

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

