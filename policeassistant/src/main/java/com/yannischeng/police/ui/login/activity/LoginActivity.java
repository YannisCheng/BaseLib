package com.yannischeng.police.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yannischeng.guolvtj.R;
import com.yannischeng.police.base.BaseActivity;
import com.yannischeng.police.base.BaseApplication;
import com.yannischeng.police.bean.CheckCodeBean;
import com.yannischeng.police.ui.login.presenter.LoginPresenter;
import com.yannischeng.police.ui.login.view.ILoginView;
import com.yannischeng.police.ui.main.activity.MainActivity;
import com.yannischeng.police.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * LoginActivity
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.login_uname)
    EditText mLoginUname;
    @BindView(R.id.login_upwd)
    EditText mLoginUpwd;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.checkBox_remember)
    CheckBox mCheckBoxRemember;
    @BindView(R.id.checkBox_auto_login)
    CheckBox mCheckBoxAutoLogin;
    @BindView(R.id.pwd_visible)
    CheckBox mPwdVisible;
    @BindView(R.id.login_register)
    TextView mLoginegister;
    @BindView(R.id.login_forget)
    TextView mLoginForget;

    private LoginPresenter mLoginPresenter;
    private String userName, userPwd;
    private boolean netIsOK = true;
    private boolean isRemember = false;
    private boolean isAutoLogin = false;
    private boolean isVisible = false;
    private Context mContext = LoginActivity.this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter();
        //userName = "admin";
        //userPwd = "123456";
        mLoginPresenter.setView(this,mContext);
        ButterKnife.bind(this);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 测试Url
                mLoginPresenter.returnCheckCodeData("123123");
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                dealInput(v);
                startActivity(new Intent(LoginActivity.this, TestActivity.class));
            }
        });

        //获取checkbox的值
        isRemember = BaseApplication.getInstance().mSharedPreferences.getBoolean("remember", false);
        isAutoLogin = BaseApplication.getInstance().mSharedPreferences.getBoolean("auto_login", false);
        isVisible = BaseApplication.getInstance().mSharedPreferences.getBoolean("visible", false);

        //初始化数据
        initData();

        //CheckBox - 界面状态变化
        dealCheckBox();
    }

    private void dealCheckBox() {

        //记住密码
        mCheckBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (!"".equals(mLoginUname.getText().toString()) && !"".equals(mLoginUpwd.getText().toString())) {
                        mCheckBoxRemember.setChecked(true);
                        //设置根据点击选择图片
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp);
                        Drawable drawable2 = getResources().getDrawable(R.drawable.ic_account_circle_black_24dp);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        mLoginUpwd.setCompoundDrawables(drawable, null, null, null);
                        mLoginUname.setCompoundDrawables(drawable2, null, null, null);

                        mLoginUpwd.setTextColor(getResources().getColor(R.color.Blue_700));
                        mLoginUname.setTextColor(getResources().getColor(R.color.Blue_700));
                        mCheckBoxRemember.setTextColor(getResources().getColor(R.color.Blue_700));

                        BaseApplication.getInstance().editor.putString("name", mLoginUname.getText().toString());
                        BaseApplication.getInstance().editor.putString("pwd", mLoginUpwd.getText().toString());
                        BaseApplication.getInstance().editor.putBoolean("remember", true);
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                        mCheckBoxRemember.setChecked(false);
                    }
                } else {
                    //设置根据点击选择图片
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_lock_open_ccc_24dp);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mLoginUpwd.setCompoundDrawables(drawable, null, null, null);

                    Drawable drawable2 = getResources().getDrawable(R.drawable.ic_account_circle_ccc_24dp);
                    drawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mLoginUname.setCompoundDrawables(drawable2, null, null, null);

                    mLoginUpwd.setTextColor(getResources().getColor(R.color.Grey_400));
                    mLoginUname.setTextColor(getResources().getColor(R.color.Grey_400));
                    mCheckBoxRemember.setTextColor(getResources().getColor(R.color.Grey_400));

                    BaseApplication.getInstance().editor.putString("name", "");
                    BaseApplication.getInstance().editor.putString("pwd", "");
                    BaseApplication.getInstance().editor.putBoolean("remember", false);
                }
            }
        });

        //自动登录
        mCheckBoxAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!"".equals(mLoginUname.getText().toString()) && !"".equals(mLoginUpwd.getText().toString())) {
                        mCheckBoxAutoLogin.setChecked(true);
                        BaseApplication.getInstance().editor.putBoolean("auto_login", true);
                        mCheckBoxAutoLogin.setTextColor(getResources().getColor(R.color.Blue_700));
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                        mCheckBoxAutoLogin.setChecked(false);
                    }
                } else {
                    BaseApplication.getInstance().editor.putBoolean("auto_login", false);
                    mCheckBoxAutoLogin.setTextColor(getResources().getColor(R.color.Grey_400));
                }
            }
        });

        //设置密码的显隐式
        mPwdVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    BaseApplication.getInstance().editor.putBoolean("visible", true);
                    mLoginUpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    BaseApplication.getInstance().editor.putBoolean("visible", false);
                    mLoginUpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void initData() {
        //填写信息
        if (isRemember) {
            mCheckBoxRemember.setChecked(true);
            //设置根据点击选择图片
            Drawable drawable = getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mLoginUpwd.setCompoundDrawables(drawable, null, null, null);
            Drawable drawable2 = getResources().getDrawable(R.drawable.ic_account_circle_black_24dp);
            drawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mLoginUname.setCompoundDrawables(drawable2, null, null, null);

            mLoginUpwd.setTextColor(getResources().getColor(R.color.Blue_700));
            mLoginUname.setTextColor(getResources().getColor(R.color.Blue_700));
            mCheckBoxRemember.setTextColor(getResources().getColor(R.color.Blue_700));

            mLoginUname.setText(BaseApplication.getInstance().mSharedPreferences.getString("name", "用户名"));
            mLoginUpwd.setText(BaseApplication.getInstance().mSharedPreferences.getString("pwd", "密码"));
        } else {
            mCheckBoxRemember.setChecked(false);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_lock_open_ccc_24dp);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            Drawable drawable2 = getResources().getDrawable(R.drawable.ic_account_circle_ccc_24dp);
            drawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mLoginUname.setCompoundDrawables(drawable2, null, null, null);

            mLoginUpwd.setCompoundDrawables(drawable, null, null, null);
            mLoginUpwd.setTextColor(getResources().getColor(R.color.Grey_400));
            mLoginUname.setTextColor(getResources().getColor(R.color.Grey_400));
            mCheckBoxRemember.setTextColor(getResources().getColor(R.color.Grey_400));
        }

        //自动跳转
        if (isAutoLogin) {
            mCheckBoxAutoLogin.setChecked(true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            mCheckBoxAutoLogin.setChecked(false);
        }

        //密码显隐式
        if (isVisible) {
            mPwdVisible.setChecked(true);
            mLoginUpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            mPwdVisible.setChecked(false);
            mLoginUpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public void netOK() {
        mLoginBtn.setClickable(true);
        mCheckBoxAutoLogin.setClickable(true);
        mCheckBoxRemember.setClickable(true);
        mLoginBtn.setText("登录");
        mLoginBtn.setTextColor(getResources().getColor(R.color.White));
        if (!netIsOK) {
            Toast.makeText(mContext, "网络已修复", Toast.LENGTH_SHORT).show();
            mLoginBtn.setBackgroundResource(R.drawable.login_button_style);
        }

        netIsOK = true;
    }

    @Override
    public void netError() {
        mLoginBtn.setClickable(false);
        mCheckBoxAutoLogin.setClickable(false);
        mCheckBoxRemember.setClickable(false);
        mLoginBtn.setText("网络异常，无法登陆");
        mLoginBtn.setTextColor(getResources().getColor(R.color.White));
        Toast.makeText(mContext, "网络不可用,请检查你的网络", Toast.LENGTH_SHORT).show();
        netIsOK = false;
    }


    /**
     * 键盘隐藏
     *
     * @param view view
     */
    private void dealInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void startLoading(String title) {
        LoadingDialog.showLoadingDialog(LoginActivity.this);
        Log.e(TAG, "ヽ(｀Д´)ﾉ -> startLoading : " + title);
    }

    @Override
    public void stopLoading() {
        LoadingDialog.cancelLoadingDialog();
        Log.e(TAG, "ヽ(｀Д´)ﾉ -> stopLoading : ");
    }

    @Override
    public void showError(String e) {
        LoadingDialog.showWaringDialog(LoginActivity.this,"请求异常！");
        Log.e(TAG, "ヽ(｀Д´)ﾉ -> showError : " + e);
    }


    @Override
    public void userLoginDataView(CheckCodeBean checkCodeBean) {
        Log.e(TAG, "ヽ(｀Д´)ﾉ -> userLoginDataView : " + checkCodeBean.toString());
    }
}
