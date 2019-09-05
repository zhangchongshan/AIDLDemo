package com.zcs.aidldemo;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AIDLServer extends Service {
    private String TAG="service";

    private List<Book> bookList;
    public AIDLServer(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList=new ArrayList<>();
        initData();

    }
    private void initData() {
        Book book1 = new Book("活着");
        Book book2 = new Book("许三观卖血记");
        Book book3 = new Book("皮囊");
        Book book4 = new Book("无所畏");
        Book book5 = new Book("倾城之恋");
        Book book6 = new Book("老生");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
        bookList.add(book6);
    }

    private final BookController.Stub stub=new BookController.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            if (book != null) {
//                book.setName("服务器改了新书的名字 InOut");
                bookList.add(book);
                Log.e(TAG, "addBookInOut: "+book.getName() );
            } else {
                Log.e(TAG, "接收到了一个空对象 InOut");
            }
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            if (book!=null){
                bookList.add(book);
                Log.e(TAG, "addBookIn: " );
            }else {
                Log.e(TAG, "addBookIn: null" );
            }
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            if (book!=null){
                bookList.add(book);
                Log.e(TAG, "addBookOut: " );
            }else {
                Log.e(TAG, "addBookOut: null" );
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
