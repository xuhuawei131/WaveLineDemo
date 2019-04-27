package com.xuhuawei.wavelinedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.xuhuawei.wavelinedemo.paints.BasePathPaint;
import com.xuhuawei.wavelinedemo.paints.DoodlingPathPaint;
import com.xuhuawei.wavelinedemo.paints.ExcellentPaint;
import com.xuhuawei.wavelinedemo.paints.PromingPaint;

import java.util.ArrayList;
import java.util.List;

public class WavePathView extends View {

    private List<BasePathPaint> arrayList = new ArrayList<>();
    private List<BasePathPaint> doolingList = new ArrayList<>();
    private List<BasePathPaint> removingList = new ArrayList<>();
    private BasePathPaint currentInfo;
    private boolean isShowOperateBorader = false;//是否显示操作符和边框
    private int pathStyle = 0;//0优秀、1待提高、2、涂鸦
    private OnWavePathListener onWavePathListener;

    public void setOnWavePathListener(OnWavePathListener onWavePathListener) {
        this.onWavePathListener = onWavePathListener;
    }

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

    public void setPathStyle(int pathStyle) {
        this.pathStyle = pathStyle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画以前的路径
        for (BasePathPaint path : arrayList) {
            path.drawPath(canvas);
        }
        //画涂鸦
        for (BasePathPaint path : doolingList) {
            path.drawPath(canvas);
        }
        if (isShowOperateBorader && currentInfo != null) {
            currentInfo.drawFrame(canvas);
        }
    }


    private BasePathPaint getPathPaintByStyle() {
        switch (pathStyle) {
            case 0:
                return new ExcellentPaint(getContext());
            case 1:
                return new PromingPaint(getContext());
            case 2:
                return new DoodlingPathPaint(getContext());
            default:
                return new DoodlingPathPaint(getContext());
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
                    currentInfo = getPathPaintByStyle();
                    currentInfo.onEventDown(x, y);

                    if (currentInfo instanceof DoodlingPathPaint) {
                        doolingList.add(currentInfo);
                        removingList.clear();

                        if (onWavePathListener != null) {
                            onWavePathListener.onUndoEvent(true);
                            onWavePathListener.onRedoEvent(false);
                        }
                    } else {
                        arrayList.add(currentInfo);
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isShowOperateBorader) {
                    currentInfo.onEventMove(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isShowOperateBorader) {
                    currentInfo.onEventUp(x, y);
                    invalidate();
                } else {
                    if (isInDelete(x, y)) {
                        Toast.makeText(getContext(), "点击删除", Toast.LENGTH_SHORT).show();
                    }
                }
                currentInfo = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                currentInfo = null;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * undo操作
     * @return
     */
    public BasePathPaint undoDoolingPaint() {
        int length=doolingList.size();
        if (length>0) {
            BasePathPaint pathPaint= doolingList.remove(length-1);
            removingList.add(pathPaint);
            invalidate();

            if (onWavePathListener != null) {
                onWavePathListener.onRedoEvent(true);
                length=doolingList.size();
                if (length>0){
                    onWavePathListener.onUndoEvent(true);
                }else{
                    onWavePathListener.onUndoEvent(false);
                }
            }

            return pathPaint;
        }
        return null;
    }
    /**
     * redo操作
     * @return
     */
    public BasePathPaint redoDoolingPaint() {
        int length=removingList.size();
        if (length>0) {
            BasePathPaint pathPaint= removingList.remove(length-1);
            doolingList.add(pathPaint);
            invalidate();

            if (onWavePathListener != null) {
                onWavePathListener.onUndoEvent(true);
                length=removingList.size();
                if (length>0){
                    onWavePathListener.onRedoEvent(true);
                }else{
                    onWavePathListener.onRedoEvent(false);
                }
            }


            return pathPaint;
        }
        return null;
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
                currentInfo = info;
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
        if (currentInfo != null) {
            return currentInfo.isInDelRect(x, y);
        }
        return false;
    }

    public interface OnWavePathListener {
        public void onUndoEvent(boolean isCanUndo);
        public void onRedoEvent(boolean isCanRedo);
    }
}
