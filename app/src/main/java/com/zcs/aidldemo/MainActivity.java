package com.zcs.aidldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


//    private Button btnAdd,btnGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*btnAdd=findViewById(R.id.service_add);
        btnAdd.setOnClickListener(this);
        btnGet=findViewById(R.id.service_get);
        btnGet.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()){
            case R.id.service_add:{
                Log.e("TTTT", "onClick: " );
            }
            break;
            case R.id.service_get:{

            }
            break;
        }*/
    }
}
