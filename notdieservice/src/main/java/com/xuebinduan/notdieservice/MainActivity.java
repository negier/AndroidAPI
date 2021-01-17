package com.xuebinduan.notdieservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 其实就是一个普通的前台服务，要想不死，如果有手机管家的，那么需要"允许其在后台活动"，然后它就不死了。
         */
        startService(new Intent(this,NotDieService.class));


    }


}