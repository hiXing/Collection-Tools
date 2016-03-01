package com.zhx.tools;

import android.view.View;
/**
 * 
 * @author zhx
 * @date  2015-9-7上午9:17:17
 * @TODO  设置view是否显示或隐藏
 *
 */
public class IsVisiableViewUtil {
	/**
	 * Refresh view status
	 */
	public static void setVisibleGone(View view, View...views) {
		if(null != view && view.getVisibility() != View.VISIBLE)
			view.setVisibility(View.VISIBLE);
		setGone(views);
	}
	public static void setGone(View... views) {
		if(views != null && views.length > 0) {
			for(View view : views) {
				if(null != view && view.getVisibility() != View.GONE)
					view.setVisibility(View.GONE);
			}
		}
	}
	public static void setVisible(View... views) {
		if(views != null && views.length > 0) {
			for(View view : views) {
				if(null != view && view.getVisibility() != View.VISIBLE)
					view.setVisibility(View.VISIBLE);
			}
		}
	}
	public static void setEnable(View... views) {
		if(views != null && views.length > 0) {
			for(View view : views) {
				if(view != null && !view.isEnabled()) {
					view.setEnabled(true);
				}
			}
		}	
	}
	public static void setDisable(View... views) {
		if(views != null && views.length > 0) {
			for(View view : views) {
				if(view != null && view.isEnabled()) {
					view.setEnabled(false);
				}
			}
		}			
	}
	public static void setInvisible(View... views) {
		if(views != null && views.length > 0) {
			for(View view : views) {
				if(null != view && view.getVisibility() != View.INVISIBLE)
					view.setVisibility(View.INVISIBLE);
			}
		}
	}
}
