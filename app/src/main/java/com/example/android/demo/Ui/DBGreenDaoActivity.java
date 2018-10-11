package com.example.android.demo.Ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.Db.Student;
import com.example.android.demo.Db.Teacher;
import com.example.android.demo.GreenDao.TeacherDao;
import com.example.android.demo.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DBGreenDaoActivity extends BaseActivity {
    int i = 1;
    long tearcherId = 10000;
    @Override
    public int getRootViewId() {
        return R.layout.activity_dbgreen_dao;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        findViewById(R.id.addStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDaoSession.getStudentDao().insert(new Student(null,i++,"张三"+i, (long) 1,"3",""));
            }
        });

        findViewById(R.id.addTeacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Teacher> list = new ArrayList<>();
                list.add(new Teacher(null,tearcherId++,"张老师",""));
                list.add(new Teacher(null,tearcherId++,"李老师",""));
                list.add(new Teacher(null,tearcherId++,"王老师",""));
                list.add(new Teacher(null,tearcherId++,"苏老师",""));
                list.add(new Teacher(null,tearcherId++,"孙老师",""));
                mDaoSession.getTeacherDao().insertInTx(list);
            }
        });
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teacher teacher = mDaoSession.getTeacherDao().queryBuilder().where(TeacherDao.Properties.Teacher_id.eq(10001)).build().unique();
                Log.e("info", "onClick: 打印该老师的所有学生："+new Gson().toJson(teacher.getStudents()));
            }
        });

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
}
