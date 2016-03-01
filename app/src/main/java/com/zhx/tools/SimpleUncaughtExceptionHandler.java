package com.zhx.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.util.Log;


/**
 * @author Administrator
 * 收集崩溃信息
 * 在 Application中注册 {@link MyApplication.onCreate()}
 * 如果你的应用崩溃后，不调用Android默认的异常处理，也不进行杀死进程，
 * 则进程处于不可交互，即UI点击无响应状态。
 */
public class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
	private static final String LOGTAG = "SimpleUncaughtExceptionHandler";
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		Writer reslut = new StringWriter();
		PrintWriter printWriter = new PrintWriter(reslut);
		ex.printStackTrace(printWriter);
		String errReport = reslut.toString();
		Log.i(LOGTAG, "uncaughtException errorReport=" + errReport);
		
	}
	
}
