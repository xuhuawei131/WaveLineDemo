package com.xuhuawei.wavelinedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class PathInfo {
    float mDownX ;
    float mDownY;
    public Path mPath;
    public RectF rectF;
    public RectF frameRectF;
    public RectF delRectF;
    private Paint mPaint, mBorderPaint;
    private static final int MAX_DISTANCE=8;
    private static final int MAX_BORDER_MARGIN=32;
    private Bitmap  mDeleteBitmap;
    private int delBitmapWidth;
    private int delBitmapHeight;

    public PathInfo(float x, float y, Context contxt) {
         mDownX = x;
         mDownY =y;
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

        //边框画笔
        mBorderPaint = new Paint(mPaint);
        mBorderPaint.setColor(Color.parseColor("#ff000000"));
        //删除按钮
        mDeleteBitmap = BitmapFactory.decodeResource(contxt.getResources(), R.mipmap.ic_sticker_delete);
        delBitmapWidth=mDeleteBitmap.getWidth();
        delBitmapHeight=mDeleteBitmap.getHeight();
    }

    public void setEndPoint(float mEndX,Path path){
        this.mPath=path;
        float distance=Math.abs(mDownX-mEndX);
        rectF = new RectF(mDownX ,mDownY-MAX_DISTANCE,
                mDownX +distance,
                mDownY + MAX_DISTANCE);

        frameRectF= new RectF(mDownX ,mDownY-MAX_BORDER_MARGIN,
                mDownX +distance,
                mDownY + MAX_BORDER_MARGIN);

        float left=mDownX+distance-delBitmapWidth/2;
        float top=mDownY-MAX_BORDER_MARGIN-delBitmapHeight/2;
        float right=left+delBitmapWidth;
        float bottom=top+delBitmapHeight;

        delRectF= new RectF(left ,top, right, bottom);
    }

    public void drawPath(Canvas canvas){
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 是否是相同的路径
     * @param path
     * @return
     */
    public boolean isSamePath(Path path){
        if (mPath==path){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 是否在我们框架内
     * @param x
     * @param y
     * @return
     */
    public boolean isInPathRect(float x,float y){
        if (rectF.contains(x, y)) {
            return true;
        }else{
            return false;
        }
    }
    /**
     * 是否在我们框架内
     * @param x
     * @param y
     * @return
     */
    public boolean isInDelRect(float x,float y){
        if (delRectF.contains(x, y)) {
            return true;
        }else{
            return false;
        }
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
