// IBookManager.aidl
package com.example.android.demo;
import com.example.android.demo.Book;
// Declare any non-default types here with import statements

interface IBookManager {
      List<Book> getBookList();
      void addBook(in Book book);
}
