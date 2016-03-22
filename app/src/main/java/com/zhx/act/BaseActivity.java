package com.zhx.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ZHX on 2016/3/1.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected <T extends View>T $(int resId){
        return (T) findViewById(resId);
    }
}
