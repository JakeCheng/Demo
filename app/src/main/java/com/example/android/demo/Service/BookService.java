package com.example.android.demo.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.android.demo.Book;
import com.example.android.demo.IBookManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;

public class BookService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Book> mBooks;

    private IBinder iBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBooks = new ArrayList<>();
        return iBinder;
    }
}
