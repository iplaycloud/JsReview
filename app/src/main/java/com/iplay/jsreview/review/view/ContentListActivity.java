package com.iplay.jsreview.review.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;
import com.iplay.jsreview.review.model.bean.Point;
import com.iplay.jsreview.test.view.FavListFragment;

public class ContentListActivity extends BaseActivity {
    public static final String CONTENT_TYPE_KEY = "content_type_key";

    public static final int LIST_TYPE_REVIEW_CONTENT = 1;
    public static final int LIST_TYPE_FAV_TEST = 2;

    private int mType = -1;

    private ContentListFragment mReviewContentListFragment;
    private FavListFragment mFavListFragment;

    private Point mPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initArguments();
        initToolBar();
        showOrHideToolBarNavigation(true);
        initView();
        setStatusBarCompat();
    }

    private void initArguments() {
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getIntExtra(CONTENT_TYPE_KEY, -1);

            switch (mType) {
                case LIST_TYPE_REVIEW_CONTENT:
                    mPoint = (Point) intent.getSerializableExtra(ReviewFragment.ARGUMENT_POINT_KEY);
                    break;
                case LIST_TYPE_FAV_TEST:
                    break;
            }
        }
    }

    private void initView() {
        switch (mType) {
            case LIST_TYPE_REVIEW_CONTENT:
                showReviewContentListFragment();
                break;
            case LIST_TYPE_FAV_TEST:
                showFavListFragment();
                break;
        }
    }

    private void showFavListFragment() {
        mFavListFragment = new FavListFragment();
        Bundle bundle = new Bundle();
        mFavListFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.id_content_fragment, mFavListFragment);
        fragmentTransaction.commit();

    }

    private void showReviewContentListFragment() {
        mReviewContentListFragment = new ContentListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ReviewFragment.ARGUMENT_POINT_KEY, mPoint);
        mReviewContentListFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.id_content_fragment, mReviewContentListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public String returnToolBarTitle() {
        switch (mType) {
            case LIST_TYPE_REVIEW_CONTENT:
                return mPoint.getName();
            case LIST_TYPE_FAV_TEST:
                return getString(R.string.my_fav);
            default:
                return getString(R.string.app_name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_point:
                Intent intent = new Intent(this, AddContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("point", mPoint);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
