package com.zhx.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ZHX on 2016/3/1.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected <T extends View>T $(int resId){
        return (T) findViewById(resId);
    }
}
