package com.iplay.jsreview.review.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;
import com.iplay.jsreview.review.model.bean.Content;
import com.iplay.jsreview.review.model.bean.Point;

import cn.bmob.v3.listener.SaveListener;

public class AddContentActivity extends BaseActivity {

    private View mRootView;

    private Point mPoint;

    private TextInputLayout mTiTitle, mTiContent;

    //防止多次提交数据
    private boolean isPosting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        initToolBar();
        showOrHideToolBarNavigation(true);

        initView();
        initArguments();
    }

    private void initView() {
        mTiTitle = (TextInputLayout) findViewById(R.id.ti_add_content_title);
        mTiContent = (TextInputLayout) findViewById(R.id.ti_add_content_content);
    }

    public void initArguments() {
        Intent intent = getIntent();
        if (intent != null)
            mPoint = (Point) intent.getSerializableExtra("point");
    }

    @Override
    public String returnToolBarTitle() {
        return "添加内容";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_content_commit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_content_submit:
                Toast.makeText(this, "submit", Toast.LENGTH_SHORT).show();

                String title = mTiTitle.getEditText().getText().toString();
                String content = mTiContent.getEditText().getText().toString();
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    Snackbar.make(mRootView, R.string.dont_no_text, Snackbar.LENGTH_SHORT).show();
                } else {
                    Content mContent = new Content();
                    mContent.setTitle(title);
                    mContent.setContent(content);
                    mContent.setAuthor("Unixer");
                    mContent.setSource("JsReview");
                    mContent.setCreater("Unixer");
                    mContent.setPoint(mPoint);
                    mContent.setSmall(content.substring(0, 20));

                    if (!isPosting) {
                        isPosting = true;
                        mContent.save(AddContentActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                isPosting = false;
                                Toast.makeText(AddContentActivity.this, R.string.submit_success, Toast.LENGTH_SHORT).show();
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
