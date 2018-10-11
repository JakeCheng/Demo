package com.example.android.demo.Ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.demo.R;

public class DemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        /**
         * 第一种方式
         * 继承Thread类，重写父类Run方法
         */
        new MyThread().start();
        /**
         * 第二种方式
         * 实现Runnable接口
         */
        new Thread(new MyThread1()).start();
        /**
         * 第三种方式
         * 匿名类
         */
        new Thread(new Runnable() {
            @Override
            public void run() { }
        }).start();
    }
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
        }
    }
    class MyThread1 implements Runnable{
        @Override
        public void run() {
        }
    }
}
