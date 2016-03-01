package com.zhx.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by ZHX on 2016/3/1.
 * 自定义滑动开关
 */
public class SwitchButton2 extends View implements View.OnTouchListener{
    private Bitmap switchOnBkg; //开关开启时背景
    private Bitmap switchOffBkg; //开关关闭时背景
    private Bitmap slipSwitchBtn; //滑动时的按钮
    private Rect onRect; //左半边矩形
    private Rect offRect; //右半边矩形

    private boolean isSlipping = false; //是否正在滑动
    private boolean isSwitchOn = false; //当前开关状态，true为开启

    private float previousX; //手指按下时的水平坐标
    private float currentX; //当前的水平坐标

    private ArrayList<OnSwitchListener> onSwitchListeners; //开关监听器


    public SwitchButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOnTouchListener(this);
        onSwitchListeners = new ArrayList<>();
    }

    public void setImageResource(int switchBkg,int slipBtn){
        switchOffBkg = BitmapFactory.decodeResource(this.getResources(),switchBkg);
        switchOnBkg = BitmapFactory.decodeResource(this.getResources(),switchBkg);
        slipSwitchBtn = BitmapFactory.decodeResource(this.getResources(),slipBtn);

        //右半边rect
        onRect = new Rect(switchOnBkg.getWidth() - slipSwitchBtn.getWidth(),
                0,switchOnBkg.getWidth(),slipSwitchBtn.getHeight());
        //左半边rect
        offRect = new Rect(0,0,slipSwitchBtn.getWidth(),slipSwitchBtn.getHeight());
    }

    public void setSwitchState(boolean switchState){
        this.isSwitchOn = switchState;
        this.invalidate();
    }
    public boolean getSwitchState(){
        return this.isSwitchOn;
    }
    public void setOnSwitchStateListener(OnSwitchListener listener){
        onSwitchListeners.add(listener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();

        float leftSlipBtnX; //滑动图标的左边坐标
        System.out.println("currentX=" + currentX + " switchOnBkg.width="
                + switchOnBkg.getWidth());
        canvas.drawBitmap(switchOnBkg,matrix,paint);
        if(isSlipping){
            //如果正在滑动
            if(currentX > switchOnBkg.getWidth()){
                leftSlipBtnX = switchOnBkg.getWidth()-slipSwitchBtn.getWidth();
            }else{
                leftSlipBtnX = currentX - slipSwitchBtn.getWidth();
            }
        }else{
            if(isSwitchOn){
                leftSlipBtnX = switchOnBkg.getWidth()-slipSwitchBtn.getWidth();
            }else{
                leftSlipBtnX = 0;
            }
        }

        if(leftSlipBtnX <0){
            leftSlipBtnX = 0;
        }else if(leftSlipBtnX >switchOnBkg.getWidth()-slipSwitchBtn.getWidth()){
            leftSlipBtnX = switchOnBkg.getWidth() -slipSwitchBtn.getWidth();
        }

        canvas.drawBitmap(slipSwitchBtn,leftSlipBtnX,0,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchOnBkg.getWidth(),switchOnBkg.getHeight());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_DOWN:
                isSlipping  = true;
                break;
            case MotionEvent.ACTION_UP:
                isSlipping = false;
                boolean previousState = isSwitchOn;
                if(event.getX()>(switchOnBkg.getWidth() / 2)){
                    isSwitchOn = true;
                }else{
                    isSwitchOn = false;
                }
                if(previousState!=isSwitchOn){
                    if(onSwitchListeners.size()>0){
                        for (OnSwitchListener listener : onSwitchListeners){
                            listener.onSwitched(isSwitchOn);
                        }
                    }
                }
                break;

        }
        this.invalidate();
        return true;
    }

    public interface OnSwitchListener{
        public abstract void onSwitched(boolean isSwitchOn);
    }
}
