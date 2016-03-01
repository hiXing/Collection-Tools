package com.zhx.tools;


import com.zhx.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author zhx
 * @date  2015-8-13上午10:09:40
 * @TODO 创建桌面快捷方式
 *
 */
public class DeskShortCutUtil {
	private static final String TAG = DeskShortCutUtil.class.getSimpleName(); 
	private static Context appContext ;
	public static void createShortCut(Context context, int resourceId){
		appContext = context;
	     SharedPreferences shortcutpref = appContext.getSharedPreferences(  
	             "shortcut", Context.MODE_PRIVATE);  

	     boolean iscreated = shortcutpref.getBoolean("iscreated", false);  

	     if (!iscreated) {  
	         createDeskShortCut(resourceId);  
	     }
	}
     
     /** 
      * 创建快捷方式 
      */  
     private static  void createDeskShortCut(int resourceId) {  
         // 创建快捷方式的Intent  
         Intent shortcutIntent = new Intent(  
                 "com.android.launcher.action.INSTALL_SHORTCUT");  
         // 不允许重复创建  
         shortcutIntent.putExtra("duplicate", false);  
         // 需要显示的名称  
         shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,  
                 appContext.getString(R.string.app_name));  
   
         // 快捷图片  
         Parcelable icon = Intent.ShortcutIconResource.fromContext(  
        		 appContext, resourceId);  
   
         shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);  
   
         Intent intent = new Intent(appContext, appContext.getClass());  
//         Intent intent = new Intent(appContext, MainActivity.class);  
         // 下面两个属性是为了当应用程序卸载时桌面 上的快捷方式会删除  
         intent.setAction("android.intent.action.MAIN");  
         intent.addCategory("android.intent.category.LAUNCHER");  
         // 点击快捷图片，运行的程序主入口  
         shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);  
         // 发送广播。OK  
         appContext.sendBroadcast(shortcutIntent);  
   
         // 在配置文件中声明已经创建了快捷方式  
         appContext.getSharedPreferences("shortcut", Context.MODE_PRIVATE)  
                 .edit().putBoolean("iscreated", true).commit();  
   
         // 2.3.3系统创建快捷方式不提示  
         if (android.os.Build.VERSION.SDK_INT==10) {  
             Toast.makeText(  
                     appContext,  
                     "已创建"  
                             + appContext.getResources().getString(  
                                     R.string.app_name) + "快捷方式。",  
                     Toast.LENGTH_LONG).show();  
         }  
   
         String handSetInfo = "手机型号:" + android.os.Build.MODEL + ",SDK版本:"  
                 + android.os.Build.VERSION.SDK_INT + ",系统版本:"  
                 + android.os.Build.VERSION.RELEASE;
         Log.d(TAG, handSetInfo);
     }
    /**
     * 创建快捷方式
     *
     * @param cxt
     *            Context
     * @param icon
     *            快捷方式图标
     * @param title
     *            快捷方式标题
     * @param cls
     *            要启动的类
     */
    public static void createDeskShortCut(Context cxt, int icon, String title,
                                          Class<?> cls) throws Exception{
        // 创建快捷方式的Intent
        Intent shortcutIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        // 需要现实的名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 快捷图片
        Parcelable ico = Intent.ShortcutIconResource.fromContext(
                cxt.getApplicationContext(), icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ico);
        Intent intent = new Intent(cxt, cls);
        // 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
//        Intent installer = new Intent();
//        installer.putExtra("duplicate", false);
//      installer.putExtra("android.intent.extra.shortcut.INTENT", installer);
//      installer.putExtra("android.intent.extra.shortcut.NAME", "XXXX");
//      installer.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(contxt, R.drawable.icon));
//      installer.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        // 点击快捷图片，运行的程序主入口
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        // 发送广播。OK
        cxt.sendBroadcast(shortcutIntent);
    }
}
