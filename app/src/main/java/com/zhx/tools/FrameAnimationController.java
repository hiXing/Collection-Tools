
package com.zhx.tools;

import com.zhx.views.SwitchButton;

import android.os.Handler;
import android.os.Message;
/**
 * @author zhx
 * @date  2015年10月22日上午10:06:25
 * @TODO {@link SwitchButton}依赖的动画控制器 
 *
 */
public class FrameAnimationController {
    private static final int MSG_ANIMATE = 1000;

    public static final int ANIMATION_FRAME_DURATION = 1000 / 60;

    private static final Handler mHandler = new AnimationHandler();

    private FrameAnimationController() {
        throw new UnsupportedOperationException();
    }

    public static void requestAnimationFrame(Runnable runnable) {
        Message message = new Message();
        message.what = MSG_ANIMATE;
        message.obj = runnable;
        mHandler.sendMessageDelayed(message, ANIMATION_FRAME_DURATION);
    }

    public static void requestFrameDelay(Runnable runnable, long delay) {
        Message message = new Message();
        message.what = MSG_ANIMATE;
        message.obj = runnable;
        mHandler.sendMessageDelayed(message, delay);
    }

    private static class AnimationHandler extends Handler {
        @Override
		public void handleMessage(Message m) {
            switch (m.what) {
                case MSG_ANIMATE:
                    if (m.obj != null) {
                        ((Runnable) m.obj).run();
                    }
                    break;
            }
        }
    }
}
