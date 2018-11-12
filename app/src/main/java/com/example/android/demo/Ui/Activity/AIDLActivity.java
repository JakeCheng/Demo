package com.example.android.demo.Ui.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.Book;
import com.example.android.demo.IBookManager;
import com.example.android.demo.R;
import com.example.android.demo.Service.BookService;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class AIDLActivity extends BaseActivity {
    @BindView(R.id.tv_show)
    TextView tv_show;
    private IBookManager iBookManager;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iBookManager = null;
        }
    };
    @Override
    public int getRootViewId() {
        return R.layout.activity_aidl;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = new Intent(this, BookService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    public void initPresenter() {

    }
    @OnClick({ R.id.btn_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Random random = new Random();
                int id = random.nextInt(100);
                Book book = new Book(id, "book" + id);

                try {
                    iBookManager.addBook(book);
                    List<Book> bookList = iBookManager.getBookList();
//                    for (int i =0;i<bookList.size();i++){
//                        Log.e(TAG, "onClick: "+bookList.get(i).bookId+"   "+bookList.get(i).bookName+"\n");
//                    }
                    Log.e(TAG, "onClick: "+new Gson().toJson(bookList));
                    tv_show.setText(new Gson().toJson(bookList));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
