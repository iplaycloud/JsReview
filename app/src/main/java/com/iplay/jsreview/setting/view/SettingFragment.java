package com.iplay.jsreview.setting.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseFragment;
import com.iplay.jsreview.review.view.ContentListActivity;

/**
 * Author : iplay
 * Mail：iplaycloud@gmail.com
 * Description：
 */
public class SettingFragment extends BaseFragment {
    private View mRootView;
    private RelativeLayout mBtSuggest, mBtUpdate, mBtAbout, mBtFav, mBtSettingCache, mBtCreateTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_setting, container, false);
            creatViews();
        }
        //缓存的mRootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;

    }

    private void creatViews() {
        mBtSuggest = (RelativeLayout) mRootView.findViewById(R.id.bt_suggest);
        mBtUpdate = (RelativeLayout) mRootView.findViewById(R.id.bt_update);
        mBtAbout = (RelativeLayout) mRootView.findViewById(R.id.bt_about);
        mBtFav = (RelativeLayout) mRootView.findViewById(R.id.bt_my_fav);
        mBtSettingCache = (RelativeLayout) mRootView.findViewById(R.id.bt_cache);
        mBtCreateTable = (RelativeLayout) mRootView.findViewById(R.id.bt_create_table);

        BtClickListener clickListener = new BtClickListener();
        mBtSuggest.setOnClickListener(clickListener);
        mBtUpdate.setOnClickListener(clickListener);
        mBtAbout.setOnClickListener(clickListener);
        mBtFav.setOnClickListener(clickListener);
        mBtSettingCache.setOnClickListener(clickListener);
        mBtCreateTable.setOnClickListener(clickListener);

    }

    private class BtClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.bt_cache:
                    intent.setClass(getContext(), SettingCacheActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_suggest:
                    intent.setClass(getContext(), SuggestActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_update:
                    break;
                case R.id.bt_about:
                    intent.setClass(getContext(), AboutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_my_fav:
                    intent.setClass(getContext(), ContentListActivity.class);
                    intent.putExtra(ContentListActivity.CONTENT_TYPE_KEY, ContentListActivity.LIST_TYPE_FAV_TEST);
                    startActivity(intent);
                    break;
                case R.id.bt_create_table:
                    intent.setClass(getContext(), CreateTableActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
