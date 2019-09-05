package com.zcs.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zcs.aidldemo.Book;
import com.zcs.aidldemo.BookController;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG="Client";

    private BookController bookController;
    private boolean connected;
    private List<Book> bookList;

    private Button btnAdd,btnGet,btnAddIn,btnAddOut;

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bookController=BookController.Stub.asInterface(iBinder);
            connected=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            connected=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd=findViewById(R.id.client_add);
        btnAdd.setOnClickListener(this);
        btnGet=findViewById(R.id.client_get);
        btnGet.setOnClickListener(this);
        btnAddIn=findViewById(R.id.client_add_in);
        btnAddIn.setOnClickListener(this);
        btnAddOut=findViewById(R.id.client_add_out);
        btnAddOut.setOnClickListener(this);
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected){
            unbindService(serviceConnection);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.client_get:{
              if (connected){
                  try {
                      bookList=bookController.getBookList();
                  } catch (RemoteException e) {
                      e.printStackTrace();
                  }
                  log();
              }
            }
            break;
            case R.id.client_add:{
                if (connected) {
                    Book book = new Book("这是一本新书 InOut");
                    try {
                        bookController.addBookInOut(book);
                        Log.e(TAG, "向服务器以InOut方式添加了一本新书");
                        Log.e(TAG, "新书名：" + book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
            case R.id.client_add_in:{
                Book book=new Book("这是一本新书 IN");
                try {
                    bookController.addBookIn(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.client_add_out:{
                Book book=new Book("这是一本新书 OUT");
                try {
                    bookController.addBookOut(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.zcs.aidldemo");
        intent.setAction("com.zcs.aidldemo.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    private void log() {
        for (Book book : bookList) {
            Log.e(TAG, book.toString());
        }
    }
}
