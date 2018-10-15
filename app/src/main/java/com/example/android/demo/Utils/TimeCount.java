package com.example.android.demo.Utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时
 */
public class TimeCount extends CountDownTimer {
    TextView tv;
    public TimeCount(TextView tv, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tv.setTextColor(Color.parseColor("#333333"));
        tv.setClickable(false);
        tv.setText(millisUntilFinished / 1000 +" s");
    }

    @Override
    public void onFinish() {
        tv.setText("重获验证码");
        tv.setTextColor(Color.parseColor("#F1922C"));
        tv.setClickable(true);
        tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }
}
