package com.xuhuawei.wavelinedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WavePathView extends View {
    private float mLastX, mLastY;
    private float mCurrentX, mCurrentY;
    private Path mPath;

    private static final int MAX_HEIGHT = 8;
    private static final int MAX_ALL_HEIGHT = MAX_HEIGHT * 2;
    private static final double FACTORY_SCALE = Math.PI / 18;


    private List<PathInfo> arrayList = new ArrayList<>();

    private boolean isShowOperateBorader = false;//是否显示操作符和边框
    private PathInfo currentInfo;
    private Paint mPaint;
    public WavePathView(Context context) {
        super(context);
        init();
    }

    public WavePathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WavePathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //画笔
        mPaint = new Paint();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setFilterBitmap(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        float mDrawSize = DimenUtils.dp2pxInt(3);
        mPaint.setStrokeWidth(mDrawSize);
        mPaint.setColor(0xFF000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画当前的路径
        if (mPath != null) {
            canvas.drawPath(mPath,mPaint);
        }
        //画以前的路径
        for (PathInfo path : arrayList) {
            if (!path.isSamePath(mPath)) {
                path.drawPath(canvas);
            }
        }
        if (isShowOperateBorader &&currentInfo!=null){
            currentInfo.drawFrame(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (isInController(x, y)) {
                    isShowOperateBorader = true;
                } else {
                    isShowOperateBorader = false;
                }
                mLastX = x;
                mLastY = y;

                mCurrentX = x;
                mCurrentY = y;

                mPath = new Path();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isShowOperateBorader) {
                    if (Math.abs(x - mLastX) > 5) {
                        mPath.reset();

                        float moveX = mLastX;
                        float moveY = getSinY(moveX, mLastY);
                        mPath.moveTo(moveX, moveY);

                        for (float i = mLastX; i < x; i += 5) {
                            moveX = i;
                            moveY = getSinY(moveX, mLastY);
                            mPath.lineTo(moveX, moveY);
                        }
                        mCurrentX = x;
                        mCurrentY = y;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isShowOperateBorader) {
                    PathInfo info = new PathInfo(mLastX, mLastY,getContext());
                    info.setEndPoint(x, mPath);
                    arrayList.add(info);
                    invalidate();
                }else{
                    if (isInDelete(x,y)){
                        Toast.makeText(getContext(),"点击删除",Toast.LENGTH_SHORT).show();
                    }
                }
                currentInfo=null;
                break;
            case MotionEvent.ACTION_CANCEL:
                currentInfo=null;
                mPath.reset();
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 获取sin值
     *
     * @param moveX
     * @param mCurrentY
     * @return
     */
    private float getSinY(float moveX, float mCurrentY) {
        float moveY = (int) (MAX_ALL_HEIGHT * Math.sin(moveX * FACTORY_SCALE) + mCurrentY);
        return moveY;
    }

    /**
     * 是否点击了控制按钮
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isInController(float x, float y) {
        for (PathInfo info : arrayList) {
            if (info.isInPathRect(x, y)) {
                currentInfo=info;
                return true;
            }
        }
        return false;
    }
    /**
     * 是否点击了控制按钮
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isInDelete(float x, float y) {
        if (currentInfo!=null){
            return currentInfo.isInDelRect(x,y);
        }
        return false;
    }

}
