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

    @Override
    public int getRootViewId() {
        return R.layout.activity_dbgreen_dao;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        findViewById(R.id.addStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDaoSession.getStudentDao().insert(new Student(null,"001","张三", (long) 1,"3",""));
            }
        });

        findViewById(R.id.addTeacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Teacher> list = new ArrayList<>();
                list.add(new Teacher(null,"0000001","张老师",""));
                list.add(new Teacher(null,"0000002","李老师",""));
                list.add(new Teacher(null,"0000003","王老师",""));
                list.add(new Teacher(null,"0000004","苏老师",""));
                list.add(new Teacher(null,"0000005","孙老师",""));
                mDaoSession.getTeacherDao().insertInTx(list);
            }
        });
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teacher teacher = mDaoSession.getTeacherDao().queryBuilder().where(TeacherDao.Properties.Teacher_name.eq("张老师")).build().unique();
                Log.e("info", "onClick: 打印该老师的所有学生："+new Gson().toJson(teacher.getStudents()));
            }
        });
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
