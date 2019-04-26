package com.xuhuawei.wavelinedemo.paints;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

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

    /**
     * 画边框和删除按钮
     * @param canvas
     */
    public void drawFrame(Canvas canvas){
        canvas.drawLine(frameRectF.left, frameRectF.top, frameRectF.right, frameRectF.top, mBorderPaint);
        canvas.drawLine(frameRectF.right, frameRectF.top, frameRectF.right, frameRectF.bottom, mBorderPaint);
        canvas.drawLine(frameRectF.left, frameRectF.bottom, frameRectF.right, frameRectF.bottom, mBorderPaint);
        canvas.drawLine(frameRectF.left, frameRectF.top, frameRectF.left, frameRectF.bottom, mBorderPaint);

        canvas.drawBitmap(mDeleteBitmap,delRectF.left,delRectF.top,null);

    }
}
