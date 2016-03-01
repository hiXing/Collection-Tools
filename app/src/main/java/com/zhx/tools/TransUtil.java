package com.zhx.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 转换工具
 * @author {acorn}
 *
 */
public class TransUtil {
	public static int obj2Int(Object o) {
		return ((Number) o).intValue();
	}

	public static float obj2Float(Object o) {
		return ((Number) o).floatValue();
	}

	/**
	 * 角度转弧度
	 * 
	 * @param angle
	 * @return
	 */
	public static double angle2radians(float angle) {
		return angle / 180f * Math.PI;
	}

	/**
	 * 弧度转角度
	 * 
	 * @param radians
	 * @return
	 */
	public static double radians2angle(double radians) {
		return 180f * radians / Math.PI;
	}
	
	/**
	  * 把dip单位转成px单位
	  *
	  * @param context
	  *            context对象
	  * @param dip
	  *            dip数值
	  * @return
	  */
	 public static int formatDipToPx(Context context, int dip) {
	  DisplayMetrics dm = new DisplayMetrics();
	  ((Activity) context).getWindowManager().getDefaultDisplay()
	    .getMetrics(dm);
	  return (int) Math.ceil(dip * dm.density);
	 }

	 /**
	  * 把px单位转成dip单位
	  *
	  * @param context
	  *            context对象
	  * @param px
	  *            px数值
	  * @return
	  */
	 public static int formatPxToDip(Context context, int px) {
	  DisplayMetrics dm = new DisplayMetrics();
	  ((Activity) context).getWindowManager().getDefaultDisplay()
	    .getMetrics(dm);
	  return (int) Math.ceil(((px * 160) / dm.densityDpi));
	 } 

}
