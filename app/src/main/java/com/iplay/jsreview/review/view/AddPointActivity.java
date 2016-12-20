package com.iplay.jsreview.review.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;
import com.iplay.jsreview.review.model.bean.Point;
import com.iplay.jsreview.review.model.bean.Unit;
import com.iplay.jsreview.review.view.adapter.SpinnerArrayAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class AddPointActivity extends BaseActivity {

    ArrayAdapter<String> adapter;
    // 建立数据源
    ArrayList mItems = new ArrayList();

    private View mRootView;

    private ArrayList<Unit> unitArrayList = new ArrayList<>();
    private TextInputLayout mUnit;
    private EditText et_unit;
    private TextInputLayout mPoint;
    private Spinner sp_select;
    //防止多次提交数据
    private boolean isPosting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_add_point, null);
        setContentView(mRootView);

        initToolBar();
        showOrHideToolBarNavigation(true);

        initView();
        initEvent();
    }

    private void initEvent() {

        readUnit();

        sp_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Toast.makeText(AddPointActivity.this, "你点击的是:" + mItems.get(pos), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void readUnit() {

        //执行查询，查询单元表 取出所有单元
        final ArrayList<String> list = new ArrayList<>();

        //初始化Bmob查询类
        BmobQuery<Unit> query = new BmobQuery<>();

        query.findObjects(this, new FindListener<Unit>() {
            @Override
            public void onSuccess(final List<Unit> unitList) {
                //根据查询的所有单元，请求所有的知识点数据
                //requestPointByUnits(unitList);

                unitArrayList.addAll(unitList);

                for (Unit u : unitList) {
                    list.add(u.getName());
                }

                Logger.i("findObject success: " + list.toString());

                mItems.addAll(list);

                // 建立Adapter并且绑定数据源
                adapter = new SpinnerArrayAdapter(AddPointActivity.this, android.R.layout.simple_spinner_item, mItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //绑定 Adapter到控件
                sp_select.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {
                //toastError(mLoadingLayout, getContext());
            }
        });
    }

    private void initView() {
        mPoint = (TextInputLayout) findViewById(R.id.ti_add_point_point);
        sp_select = (Spinner) findViewById(R.id.sp_select);
    }

    @Override
    public String returnToolBarTitle() {
        return "添加知识点";
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

                String unit = sp_select.getSelectedItem().toString();
                String point = mPoint.getEditText().getText().toString();
                if (TextUtils.isEmpty(point)) {
                    Snackbar.make(mRootView, R.string.dont_no_text, Snackbar.LENGTH_SHORT).show();
                } else {
                    addPoint2Bmob(unit, point);
                }

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addPoint2Bmob(String unit, String point) {

        Point mPoint = new Point();
        mPoint.setName(point);

        for (Unit u : unitArrayList) {
            if (u.getName().equals(unit)) {
                mPoint.setUnit(u);
            }
        }
        mPoint.setColor(4);

        if (!isPosting) {
            isPosting = true;
            mPoint.save(AddPointActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    isPosting = false;
                    Toast.makeText(AddPointActivity.this, R.string.submit_success, Toast.LENGTH_SHORT).show();
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
}

