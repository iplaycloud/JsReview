package com.iplay.jsreview.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;
import com.iplay.jsreview.commons.utils.DoubleClickExitHelper;
import com.iplay.jsreview.review.model.bean.Unit;
import com.iplay.jsreview.review.view.AddPointActivity;
import com.iplay.jsreview.review.view.ReviewFragment;
import com.iplay.jsreview.search.view.SearchActivity;
import com.iplay.jsreview.setting.view.SettingFragment;
import com.iplay.jsreview.test.view.AddTestActivity;
import com.iplay.jsreview.test.view.TestFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    protected TextView mCount;
    private DoubleClickExitHelper mDoubleClickExit;
    private FragmentTabHost mFragmentTabHost;
    private View mRootView;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    private LinearLayout mTabReview;
    private LinearLayout mTabTest;
    private LinearLayout mTabSettings;

    private ImageButton mImgReview;
    private ImageButton mImgTest;
    private ImageButton mImgSettings;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mRootView);

        initToolBar();
        initView();
        initEvent();
        setStatusBarCompat();
    }

    private void initView() {
        //测试栏目的题目统计TextView
        mDoubleClickExit = new DoubleClickExitHelper(this);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabReview = (LinearLayout) findViewById(R.id.id_tab_review);
        mTabTest = (LinearLayout) findViewById(R.id.id_tab_test);
        mTabSettings = (LinearLayout) findViewById(R.id.id_tab_setting);

        mImgReview = (ImageButton) findViewById(R.id.id_img_review);
        mImgTest = (ImageButton) findViewById(R.id.id_img_test);
        mImgSettings = (ImageButton) findViewById(R.id.id_img_setting);

        mFragments = new ArrayList<Fragment>();
        Fragment mTab01 = new ReviewFragment();
        Fragment mTab02 = new TestFragment();
        Fragment mTab03 = new SettingFragment();
        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);

        mImgReview.setImageResource(R.mipmap.icon_review_on);
    }

    private void initEvent() {
        mTabReview.setOnClickListener(this);
        mTabTest.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_review:
                setSelect(0);
                break;
            case R.id.id_tab_test:
                setSelect(1);
                break;
            case R.id.id_tab_setting:
                setSelect(2);
                break;

            default:
                break;
        }
    }

    private void setSelect(int i) {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    private void setTab(int i) {
        resetImgs();

        switch (i) {
            case 0:
                mImgReview.setImageResource(R.mipmap.icon_review_on);
                break;
            case 1:
                mImgTest.setImageResource(R.mipmap.icon_test_on);
                break;
            case 2:
                mImgSettings.setImageResource(R.mipmap.icon_other_on);
                break;
        }
    }

    private void resetImgs() {
        mImgReview.setImageResource(R.mipmap.icon_review_off);
        mImgTest.setImageResource(R.mipmap.icon_test_off);
        mImgSettings.setImageResource(R.mipmap.icon_other_off);
    }

    public View getRootView() {
        return mRootView;
    }

    @Override
    public String returnToolBarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_r_0:
                showAddUnitWindow();
                break;

            case R.id.toolbar_r_1:
                startActivity(new Intent(this, AddPointActivity.class));
                break;
            case R.id.toolbar_r_2:
                startActivity(new Intent(this, AddTestActivity.class));
                break;

            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddUnitWindow() {

        final EditText et = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("新建单元："); //设置标题
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setView(et);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                Unit mUnit = new Unit();

                String name = et.getText().toString().trim();
                if (name.equals("")) {
                    Snackbar.make(mRootView, "单元名为空", Snackbar.LENGTH_SHORT).show();
                    return;
                } else {
                    mUnit.setName(name);

                    mUnit.save(mContext, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Snackbar.make(mRootView, R.string.submit_success, Snackbar.LENGTH_SHORT).show();
                            //finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Snackbar.make(mRootView, R.string.submit_failed, Snackbar.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        });

        builder.setNegativeButton("取消", null);
        builder.create().show();
    }


    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
