/*
 * Copyright (c) 2016. Vv <iplaycloud@gmail.com><http://www.v-sounds.com/>
 *
 * This file is part of AndroidReview (Android面试复习)
 *
 * AndroidReview is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  AndroidReview is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with AndroidReview.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.iplay.jsreview.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;
import com.iplay.jsreview.commons.utils.DoubleClickExitHelper;
import com.iplay.jsreview.review.view.ReviewFragment;
import com.iplay.jsreview.setting.view.SettingFragment;
import com.iplay.jsreview.test.view.TestFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private DoubleClickExitHelper mDoubleClickExit;
    private FragmentTabHost mFragmentTabHost;
    protected TextView mCount;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        setContentView(mRootView);

        initToolBar();
        initView();
        initEvent();
        setStatusBarCompat();
    }

    private void initView()
    {
        //测试栏目的题目统计TextView
        mCount = (TextView) findViewById(R.id.tv_count);
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

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
    }

    private void initEvent()
    {
        mTabReview.setOnClickListener(this);
        mTabTest.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
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

    private void setSelect(int i)
    {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    private void setTab(int i)
    {
        resetImgs();

        switch (i)
        {
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

    private void resetImgs()
    {
        mImgReview.setImageResource(R.mipmap.icon_review_off);
        mImgTest.setImageResource(R.mipmap.icon_test_off);
        mImgSettings.setImageResource(R.mipmap.icon_other_off);
    }

    public View getRootView() {
        return mRootView;
    }


    public void setTextCount(int count){
        mCount.setText("已做"+count+"题");
    }

    @Override
    public String returnToolBarTitle() {
        return getString(R.string.app_name);
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
