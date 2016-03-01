package com.zhx.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
/**
 * @author zhx
 * @date  2015年10月8日上午11:46:53
 * @TODO 水平滚动视图，可设置阻尼效果
 *
 */
public class ColumnHorizontalScrollView extends HorizontalScrollView {
	/** 传入整体布局  */
	private View ll_content;
	/** 传入更多栏目选择布局 */
	private View ll_more;
	/** 传入拖动栏布局 */
	private View rl_column;
	/** 左阴影图片 */
	private ImageView leftImage;
	/** 右阴影图片 */
	private ImageView rightImage;
	/** 屏幕宽度 */
	private int mScreenWitdh = 0;
	/** 父类的活动activity */
	private Activity activity;
	/**
	 * 阻尼效果
	 */
	private View inner;// 子View  
	  
    private float x;// 点击时y坐标  
  
    private Rect normal = new Rect();// 矩形(这里只是个形式，只是用于判断是否需要动画.)  
  
    private boolean isCount = false;// 是否开始计算  
  
    private boolean isMoveing = false;// 是否开始移动.  
  
    // private ImageView imageView;  
  
    private int initLeft;// 初始高度  
    private int left;// 拖动时时高度。  
	/********/
	public ColumnHorizontalScrollView(Context context) {
		super(context);
	}

	public ColumnHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ColumnHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	/*** 
     * 根据 XML 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate 
     * 方法，也应该调用父类的方法，使该方法得以执行. 
     */ 
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {  
            inner = getChildAt(0);  
        }  
	}
	/** touch 事件处理 **/  
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {  
            commOnTouchEvent(ev);  
        }  
		return super.onTouchEvent(ev);
	}
	/** 
     * 滑动事件(让滑动的速度变为原来的1/2) 
     */
	@Override
	public void fling(int velocityX) {
		// TODO Auto-generated method stub
		super.fling(velocityX / 2);
		
	}
	/*** 
     * 触摸事件 
     *  
     * @param ev 
     */  
    public void commOnTouchEvent(MotionEvent ev) {  
        int action = ev.getAction();  
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
            break;  
  
        case MotionEvent.ACTION_UP:  
            isMoveing = false;  
            // 手指松开.  
            if (isNeedAnimation()) {  
                animation();  
            }  
  
            break;  
        /*** 
         * 排除出第一次移动计算，因为第一次无法得知y坐标， 在MotionEvent.ACTION_DOWN中获取不到， 
         * 因为此时是MyScrollView的touch事件传递到到了LIstView的孩子item上面.所以从第二次计算开始. 
         * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归0. 之后记录准确了就正常执行. 
         */  
        case MotionEvent.ACTION_MOVE:  
            final float preX = x;// 按下时的y坐标  
            float nowX = ev.getX();// 时时y坐标  
            int deltaX = (int) (nowX - preX);// 滑动距离  
            if (!isCount) {  
                deltaX = 0; // 在这里要归0.  
            }  
            // 当滚动到最上或者最下时就不会再滚动，这时移动布局  
            isNeedMove();  
  
            if (isMoveing) {  
                // 初始化头部矩形  
                if (normal.isEmpty()) {  
                    // 保存正常的布局位置  
                    normal.set(inner.getLeft(), inner.getTop(),  
                            inner.getRight(), inner.getBottom());  
                }  
  
                // 移动布局  
                inner.layout(inner.getLeft() + deltaX / 3, inner.getTop(),  
                        inner.getRight() + deltaX / 3, inner.getBottom());  
  
                left += (deltaX / 6);  
            }  
  
            isCount = true;  
            x = nowX;  
            break;  
  
        default:  
            break;  
  
        }  
    } 
    /*** 
     * 回缩动画 
     */  
    public void animation() {  
        TranslateAnimation taa = new TranslateAnimation(0, 0, left + 200,  
                initLeft + 200);  
        taa.setDuration(200);  
        TranslateAnimation ta = null;  
        // 开启移动动画  
        ta = new TranslateAnimation(inner.getLeft(), normal.left, 0, 0);  
        ta.setDuration(200);  
        inner.startAnimation(ta);  
        // 设置回到正常的布局位置  
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);  
        normal.setEmpty();  
  
        isCount = false;  
        x = 0;// 手指松开要归0.  
  
    }  
  
    // 是否需要开启动画  
    public boolean isNeedAnimation() {  
        return !normal.isEmpty();  
    }  
  
    /*** 
     * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的总高度 
     *  
     * getHeight()：获取的是屏幕的高度 
     *  
     * @return 
     */  
    public void isNeedMove() {  
        int scrollY = getScrollY();  
        if (scrollY == 0) {  
            isMoveing = true;  
        }  
    }
    /////////////////////////////////////////////////////
	/** 
	 * 在拖动的时候执行
	 * */
	@Override
	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		// TODO Auto-generated method stub
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		shade_ShowOrHide();
		if(!activity.isFinishing() && ll_content !=null && leftImage!=null && rightImage!=null && ll_more!=null && rl_column !=null){
			if(ll_content.getWidth() <= mScreenWitdh){
				leftImage.setVisibility(View.GONE);
				rightImage.setVisibility(View.GONE);
			}
		}else{
			return;
		}
		if(paramInt1 ==0){
			leftImage.setVisibility(View.GONE);
			rightImage.setVisibility(View.VISIBLE);
			return;
		}
		if(ll_content.getWidth() - paramInt1 + ll_more.getWidth() + rl_column.getLeft() == mScreenWitdh){
			leftImage.setVisibility(View.VISIBLE);
			rightImage.setVisibility(View.GONE);
			return;
		}
		leftImage.setVisibility(View.VISIBLE);
	   rightImage.setVisibility(View.VISIBLE);
	}
	/** 
	 * 传入父类布局中的资源文件
	 * */
	public void setParam(Activity activity, int mScreenWitdh,View paramView1,ImageView paramView2, ImageView paramView3 ,View paramView4,View paramView5){
		this.activity = activity;
		this.mScreenWitdh = mScreenWitdh;
		ll_content = paramView1;
		leftImage = paramView2;
		rightImage = paramView3;
		ll_more = paramView4;
		rl_column = paramView5;
	}
	/** 
	 * 判断左右阴影的显示隐藏效果
	 * */
	public void shade_ShowOrHide() {
		if (!activity.isFinishing() && ll_content != null) {
			measure(0, 0);
			//如果整体宽度小于屏幕宽度的话，那左右阴影都隐藏
			if (mScreenWitdh >= getMeasuredWidth()) {
				leftImage.setVisibility(View.GONE);
				rightImage.setVisibility(View.GONE);
			}
		} else {
			return;
		}
		//如果滑动在最左边时候，左边阴影隐藏，右边显示
		if (getLeft() == 0) {
			leftImage.setVisibility(View.GONE);
			rightImage.setVisibility(View.VISIBLE);
			return;
		}
		//如果滑动在最右边时候，左边阴影显示，右边隐藏
		if (getRight() == getMeasuredWidth() - mScreenWitdh) {
			leftImage.setVisibility(View.VISIBLE);
			rightImage.setVisibility(View.GONE);
			return;
		}
		//否则，说明在中间位置，左、右阴影都显示
		leftImage.setVisibility(View.VISIBLE);
		rightImage.setVisibility(View.VISIBLE);
	}
}
