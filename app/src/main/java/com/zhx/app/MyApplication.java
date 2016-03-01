package com.zhx.app;

import com.zhx.tools.SimpleUncaughtExceptionHandler;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	private static final String LOG = "MyApplication"; 

	private static MyApplication application;
	public static MyApplication getApplication(){
		return application;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(LOG, "onCreate");
		application = this;
		Thread.setDefaultUncaughtExceptionHandler(new SimpleUncaughtExceptionHandler());
	}
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
}
