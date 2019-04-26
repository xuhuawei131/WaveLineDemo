package com.xuhuawei.wavelinedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.xuhuawei.wavelinedemo.paints.BasePathPaint;
import com.xuhuawei.wavelinedemo.paints.DoodlingPathPaint;
import com.xuhuawei.wavelinedemo.paints.ExcellentPaint;

import java.util.ArrayList;
import java.util.List;

public class WavePathView extends View {

    private List<BasePathPaint> arrayList = new ArrayList<>();
    private BasePathPaint currentInfo;
    private boolean isShowOperateBorader = false;//是否显示操作符和边框

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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画以前的路径
        for (BasePathPaint path : arrayList) {
            path.drawPath(canvas);
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
//                    currentInfo=new ExcellentPaint(getContext());
                    currentInfo=new DoodlingPathPaint(getContext());
                    currentInfo.onEventDown(x,y);
                    arrayList.add(currentInfo);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isShowOperateBorader) {
                    currentInfo.onEventMove(x,y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isShowOperateBorader) {
                    currentInfo.onEventUp(x,y);
                    invalidate();
                }else{
//                    if (isInDelete(x,y)){
//                        Toast.makeText(getContext(),"点击删除",Toast.LENGTH_SHORT).show();
//                    }
                }
                currentInfo=null;
                break;
            case MotionEvent.ACTION_CANCEL:
                currentInfo=null;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 是否点击了控制按钮
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isInController(float x, float y) {
        for (BasePathPaint info : arrayList) {
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
