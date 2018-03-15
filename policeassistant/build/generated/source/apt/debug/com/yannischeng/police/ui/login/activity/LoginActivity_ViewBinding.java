// Generated code from Butter Knife. Do not modify!
package com.yannischeng.police.ui.login.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yannischeng.guolvtj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.mLoginUname = Utils.findRequiredViewAsType(source, R.id.login_uname, "field 'mLoginUname'", EditText.class);
    target.mLoginUpwd = Utils.findRequiredViewAsType(source, R.id.login_upwd, "field 'mLoginUpwd'", EditText.class);
    target.mLoginBtn = Utils.findRequiredViewAsType(source, R.id.login_btn, "field 'mLoginBtn'", Button.class);
    target.mCheckBoxRemember = Utils.findRequiredViewAsType(source, R.id.checkBox_remember, "field 'mCheckBoxRemember'", CheckBox.class);
    target.mCheckBoxAutoLogin = Utils.findRequiredViewAsType(source, R.id.checkBox_auto_login, "field 'mCheckBoxAutoLogin'", CheckBox.class);
    target.mPwdVisible = Utils.findRequiredViewAsType(source, R.id.pwd_visible, "field 'mPwdVisible'", CheckBox.class);
    target.mLoginegister = Utils.findRequiredViewAsType(source, R.id.login_register, "field 'mLoginegister'", TextView.class);
    target.mLoginForget = Utils.findRequiredViewAsType(source, R.id.login_forget, "field 'mLoginForget'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLoginUname = null;
    target.mLoginUpwd = null;
    target.mLoginBtn = null;
    target.mCheckBoxRemember = null;
    target.mCheckBoxAutoLogin = null;
    target.mPwdVisible = null;
    target.mLoginegister = null;
    target.mLoginForget = null;
  }
}
