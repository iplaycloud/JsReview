package com.iplay.jsreview.test.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iplay.jsreview.R;
import com.iplay.jsreview.commons.base.BaseActivity;
import com.iplay.jsreview.test.model.bean.Test;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class AddTestActivity extends BaseActivity {

    private View mRootView;

    //防止多次提交数据
    private boolean isPosting = false;

    private int answer_type = 1;

    private RadioGroup mRadioGroup;
    private View answer_type_1;
    private View answer_type_2;

    private TextInputLayout ti_test_title;
    private TextInputLayout ti_test_content;

    private CheckBox cb_A;
    private CheckBox cb_B;
    private CheckBox cb_C;
    private CheckBox cb_D;
    private CheckBox cb_E;
    private CheckBox cb_F;

    private EditText et_A;
    private EditText et_B;
    private EditText et_C;
    private EditText et_D;
    private EditText et_E;
    private EditText et_F;

    /*存储已有编号*/
    private ArrayList<Integer> testId = new ArrayList();

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

        ti_test_title = (TextInputLayout) findViewById(R.id.ti_test_title);
        ti_test_content = (TextInputLayout) findViewById(R.id.ti_test_content);

        cb_A = (CheckBox) findViewById(R.id.cb_A);
        cb_B = (CheckBox) findViewById(R.id.cb_B);
        cb_C = (CheckBox) findViewById(R.id.cb_C);
        cb_D = (CheckBox) findViewById(R.id.cb_D);
        cb_E = (CheckBox) findViewById(R.id.cb_E);
        cb_F = (CheckBox) findViewById(R.id.cb_F);

        et_A = (EditText) findViewById(R.id.et_A);
        et_B = (EditText) findViewById(R.id.et_B);
        et_C = (EditText) findViewById(R.id.et_C);
        et_D = (EditText) findViewById(R.id.et_D);
        et_E = (EditText) findViewById(R.id.et_E);
        et_F = (EditText) findViewById(R.id.et_F);

        answer_type_1 = findViewById(R.id.answer_type_1);
        answer_type_2 = findViewById(R.id.answer_type_2);

        //根据ID找到RadioGroup实例
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_answer_type);

        //绑定一个匿名监听器
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) AddTestActivity.this.findViewById(radioButtonId);
                //更新文本内容，以符合选中项
                Toast.makeText(AddTestActivity.this, "您选择的是：" + rb.getText(), Toast.LENGTH_SHORT).show();
                if (rb.getText().equals("选择题")) {
                    answer_type = 1;
                    answer_type_1.setVisibility(View.VISIBLE);
                    answer_type_2.setVisibility(View.GONE);
                } else if (rb.getText().equals("问答题")) {
                    answer_type = 2;
                    answer_type_1.setVisibility(View.GONE);
                    answer_type_2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initArguments() {

        //只返回Person表的objectId这列的值
        BmobQuery<Test> bmobQuery = new BmobQuery<Test>();
        bmobQuery.addQueryKeys("testId");
        bmobQuery.findObjects(AddTestActivity.this, new FindListener<Test>() {

            @Override
            public void onSuccess(List<Test> list) {

                for (Test t : list) {
                    testId.add(t.getTestId());
                }

                //Logger.i(testId.get(testId.size() - 1).toString());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
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

            case R.id.action_add_test_submit:
                Toast.makeText(this, "submit", Toast.LENGTH_SHORT).show();

                String question = ti_test_title.getEditText().getText().toString();

                String answer = ti_test_content.getEditText().getText().toString();

                String answerA = et_A.getText().toString();
                String answerB = et_B.getText().toString();
                String answerC = et_C.getText().toString();
                String answerD = et_D.getText().toString();
                String answerE = et_E.getText().toString();
                String answerF = et_F.getText().toString();

                if (TextUtils.isEmpty(question)) {
                    Snackbar.make(mRootView, "不能上传空白题目", Snackbar.LENGTH_SHORT).show();
                } else if ((answer_type == 2 && TextUtils.isEmpty(answer))) {
                    Snackbar.make(mRootView, "不能上传空白答案", Snackbar.LENGTH_SHORT).show();
                } else if ((answer_type == 1 && (!TextUtils.isEmpty(answerA) && !TextUtils.isEmpty(answerB) && !TextUtils.isEmpty(answerC) && !TextUtils.isEmpty(answerD) && !TextUtils.isEmpty(answerE) && !TextUtils.isEmpty(answerF)))) {
                    Snackbar.make(mRootView, "不能上传空白答案", Snackbar.LENGTH_SHORT).show();
                } else {
                    Test mTest = new Test();

                    mTest.setQuestion(question);

                    mTest.setTestType(answer_type);

                    mTest.setAnswer(answer);
                    mTest.setAnswerA(answerA);
                    mTest.setAnswerB(answerB);
                    mTest.setAnswerC(answerC);
                    mTest.setAnswerD(answerD);
                    mTest.setAnswerE(answerE);
                    mTest.setAnswerF(answerF);

                    /*需要查询最后一条id*/
                    mTest.setTestId(testId.size());

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

