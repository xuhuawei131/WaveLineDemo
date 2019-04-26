package com.xuhuawei.wavelinedemo.paints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.xuhuawei.wavelinedemo.DimenUtils;

public class DoodlingPathPaint extends BasePathPaint {

    public DoodlingPathPaint(Context context) {
        super(context);
    }
    @Override
    protected void initPaint(Context context) {
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setFilterBitmap(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(DimenUtils.dp2pxInt(3));
        mPaint.setColor(0xFF000000);
    }

    @Override
    public void onEventDown(float x, float y) {
        mPath.moveTo(x,y);
    }

    @Override
    public void onEventMove(float x, float y) {
        mPath.lineTo(x,y);
    }

    @Override
    public void onEventUp(float x, float y) {

    }

    @Override
    public void onEventCancel(float x, float y) {

    }

    @Override
    public void drawPath(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void drawFrame(Canvas canvas) {

    }

    @Override
    public boolean isInPathRect(float x, float y) {
        return false;
    }

    @Override
    public boolean isInDelRect(float x, float y) {
        return false;
    }
    @Override
    protected float getTransformY(float x, float y) {
        return y;
    }


}
