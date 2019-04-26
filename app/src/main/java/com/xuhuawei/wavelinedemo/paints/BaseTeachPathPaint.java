package com.xuhuawei.wavelinedemo.paints;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.xuhuawei.wavelinedemo.Const;
import com.xuhuawei.wavelinedemo.DimenUtils;
import com.xuhuawei.wavelinedemo.R;

public abstract class BaseTeachPathPaint extends BasePathPaint {
    protected Bitmap mDeleteBitmap;
    protected int delBitmapWidth;
    protected int delBitmapHeight;
    protected Paint mBorderPaint;
    protected RectF delRectF;

    public BaseTeachPathPaint(Context context) {
        super(context);
    }
    @Override
    protected void initPaint(Context context) {
        //边框画笔
        mBorderPaint = new Paint(mPaint);
        mBorderPaint.setColor(Color.parseColor("#ff000000"));
        //删除按钮
        mDeleteBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_sticker_delete);
        delBitmapWidth=mDeleteBitmap.getWidth();
        delBitmapHeight=mDeleteBitmap.getHeight();
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
            float moveY = getTransformY(moveX,startY);
            mPath.moveTo(moveX, moveY);

            for (float i = startX; i < x; i += 5) {
                moveX = i;
                moveY = getTransformY(moveX,startY);
                mPath.lineTo(moveX, moveY);
            }
        }
    }

    @Override
    public void onEventUp(float x, float y) {
        //滑动的距离
        float distance=Math.abs(startX-x);

        //图形的范围
        rectF = new RectF(startX ,startY- Const.MAX_DISTANCE,
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
    public void drawPath(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
    /**
     * 画边框和删除按钮
     * @param canvas
     */
    @Override
    public void drawFrame(Canvas canvas){
        canvas.drawLine(frameRectF.left, frameRectF.top, frameRectF.right, frameRectF.top, mBorderPaint);
        canvas.drawLine(frameRectF.right, frameRectF.top, frameRectF.right, frameRectF.bottom, mBorderPaint);
        canvas.drawLine(frameRectF.left, frameRectF.bottom, frameRectF.right, frameRectF.bottom, mBorderPaint);
        canvas.drawLine(frameRectF.left, frameRectF.top, frameRectF.left, frameRectF.bottom, mBorderPaint);

        canvas.drawBitmap(mDeleteBitmap,delRectF.left,delRectF.top,null);
    }
}
