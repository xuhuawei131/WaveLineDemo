package com.xuhuawei.wavelinedemo.paints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.xuhuawei.wavelinedemo.DimenUtils;

public class PromingPaint extends BaseTeachPathPaint {
    public PromingPaint(Context context) {
        super(context);
    }
    @Override
    protected void initPaint(Context context) {
        super.initPaint(context);
        //画笔
        mPaint = new Paint();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setFilterBitmap(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(DimenUtils.dp2pxInt(3));
        mPaint.setColor(0xFF000000);
    }

    @Override
    protected float getTransformY(float x, float y) {
        return y;
    }
}
