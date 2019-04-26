package com.xuhuawei.wavelinedemo.paints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.xuhuawei.wavelinedemo.Const;
import com.xuhuawei.wavelinedemo.DimenUtils;

public class ExcellentPaint extends BaseTeachPathPaint {


    public ExcellentPaint(Context context) {
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
    public void onEventDown(float x, float y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void onEventMove(float x, float y) {
        if (Math.abs(x - startX) > 5) {
            mPath.reset();

            float moveX = startX;
            float moveY = getTransformY(moveX,y);
            mPath.moveTo(moveX, moveY);

            for (float i = startX; i < x; i += 5) {
                moveX = i;
                moveY = getTransformY(moveX,y);
                mPath.lineTo(moveX, moveY);
            }
        }
    }

    @Override
    public void onEventUp(float x, float y) {
        //滑动的距离
        float distance=Math.abs(startX-x);

        //图形的范围
        rectF = new RectF(startX ,startY-Const.MAX_DISTANCE,
                startX +distance,
                startY + Const.MAX_DISTANCE);
        //边框范围
        frameRectF= new RectF(startX ,startY-Const.MAX_BORDER_MARGIN,
                startX +distance,
                startY + Const.MAX_BORDER_MARGIN);

        //删除按钮范围
        float left=startX+distance-delBitmapWidth/2;
        float top=startY-Const.MAX_BORDER_MARGIN-delBitmapHeight/2;
        float right=left+delBitmapWidth;
        float bottom=top+delBitmapHeight;
        delRectF= new RectF(left ,top, right, bottom);
    }

    @Override
    public void onEventCancel(float x, float y) {

    }

    @Override
    public void drawPath(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean isInPathRect(float x, float y) {
        if (rectF!=null&&rectF.contains(x, y)) {
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean isInDelRect(float x, float y) {
        if (delRectF!=null&&delRectF.contains(x, y)) {
            return true;
        }else{
            return false;
        }
    }
    @Override
    protected float getTransformY(float moveX,float moveY) {
        moveY = (int) (Const.MAX_ALL_HEIGHT * Math.sin(moveX * Const.FACTORY_SCALE) + startY);
        return moveY;
    }
}
