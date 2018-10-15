package com.example.android.demo.Ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.Db.DbManager;
import com.example.android.demo.Db.User;
import com.example.android.demo.GreenDao.UserDao;
import com.example.android.demo.R;
import com.example.android.demo.Utils.AnalyzingDataFormat;
import com.example.android.demo.Utils.RandomUtils;
import com.example.android.demo.Utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tl_email)
    TextInputLayout tl_email;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.tl_password)
    TextInputLayout tl_password;
    @BindView(R.id.et_password)
    TextInputEditText et_password;
    @Override
    public int getRootViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tl_email.setCounterEnabled(true);
        tl_email.setCounterMaxLength(11);

        tl_password.setCounterEnabled(true);
        tl_password.setCounterMaxLength(6);
        tl_password.setPasswordVisibilityToggleEnabled(true);
        tl_password.setPasswordVisibilityToggleDrawable(R.drawable.choose_login_password_show);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    tl_email.setError("不能为空");
                }else if (!AnalyzingDataFormat.isEmail(s.toString().trim())){
                    tl_email.setError("邮箱格式错误");
                }else{
                    tl_email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @OnClick({ R.id.email_sign_in_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                if (!TextUtils.isEmpty(et_email.getText().toString().trim()) && !TextUtils.isEmpty(et_password.getText().toString().trim()) &&
                        AnalyzingDataFormat.isEmail(et_email.getText().toString().trim()) && et_password.getText().toString().trim().length()<7){
                    SharePreferenceUtil.setEmail(mContext,et_email.getText().toString().trim());
                    if (DbManager.getDaoSession(mContext).getUserDao().queryBuilder().where(UserDao.Properties.Email.eq(et_email.getText().toString().trim())).build().unique() == null) {
                        DbManager.getDaoSession(mContext).getUserDao().insert(new User(Long.valueOf(RandomUtils.getRandom(6,6)), et_email.getText().toString().trim()));
                    }
                    startActivity(new Intent(mContext, MainActivity.class));
                }
                break;
        }
    }
    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
}

