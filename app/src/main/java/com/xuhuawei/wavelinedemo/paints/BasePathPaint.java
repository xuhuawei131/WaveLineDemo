package com.xuhuawei.wavelinedemo.paints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public abstract class BasePathPaint {
    protected float startX,startY;

    protected Paint mPaint=new Paint();
    protected Path mPath=new Path();
    protected RectF rectF=new RectF();
    protected RectF frameRectF=new RectF();

    protected abstract void initPaint(Context context);
    public abstract void onEventDown(float x,float y);
    public abstract void onEventMove(float x,float y);
    public abstract void onEventUp(float x,float y);
    public abstract void onEventCancel(float x,float y);

    /**
     * 画路线
     * @param canvas
     */
    public abstract void drawPath(Canvas canvas);
    /**
     * 画画边框
     * @param canvas
     */
    public abstract void drawFrame(Canvas canvas);


    /**
     * 是否坐标在path范围内
     * @param x
     * @param y
     * @return
     */
    public abstract boolean isInPathRect(float x, float y);

    /**
     * 坐标是否在删除按钮范围内
     * @param x
     * @param y
     * @return
     */
    public abstract boolean isInDelRect(float x, float y);
    /**
     * 获取转化的Y坐标
     * @param x
     * @param y
     * @return
     */
    protected abstract float getTransformY(float x,float y);


    public BasePathPaint(Context context) {
        initPaint(context);
    }
}
