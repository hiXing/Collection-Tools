package com.zhx.zxing;

import android.app.Application;
import android.content.Context;

/**
 * Created by ZHX on 2016/3/24.
 */
public class App extends Application{
    private static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
    }
    public static Context getAppContext(){
        return _context;
    }
}
