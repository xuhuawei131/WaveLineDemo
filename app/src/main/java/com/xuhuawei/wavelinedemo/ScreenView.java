package com.xuhuawei.wavelinedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScreenView extends View {
    //毫无疑问，必须要实例化此类
    public ScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //重写onDraw方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先绘制一个矩形，填充颜色
        Paint screenPaint = new Paint();
        screenPaint.setStyle(Paint.Style.FILL);
        screenPaint.setColor(Color.parseColor("#011801"));
        canvas.drawRect(30, 60, 450, 400, screenPaint);
        //在绘制网格，小鹿编写一下网格目的是为了美观，不过大家觉得难看也不要喷，你们删掉代码就好
        Paint netPaint = new Paint();
        netPaint.setStyle(Paint.Style.FILL);
        netPaint.setColor(Color.parseColor("#D4D3C3"));
        //横向网格
        for (int i = 1; i < 5; i++) {
            canvas.drawLine(30, 230 - 40 * i, 450, 230 - 40 * i, netPaint);
            canvas.drawLine(30, 230 + 40 * i, 450, 230 + 40 * i, netPaint);
        }
        //纵向网格
        for (int i = 1; i < 11; i++) {
            canvas.drawLine(40 + 40 * i, 400, 40 + 40 * i, 60, netPaint);
        }
        //绘制两条轴，便是XY轴了，
        Paint xyPaint = new Paint();
        xyPaint.setStyle(Paint.Style.STROKE);
        xyPaint.setColor(Color.parseColor("#EDA54F"));
        xyPaint.setStrokeWidth(3);//加粗，目的是为了然大家看得出它们
        canvas.drawLine(30, 230, 450, 230, xyPaint);//X轴
        canvas.drawLine(40, 60, 40, 400, xyPaint);//Y轴
        //绘制sin曲线
        Paint sinPaint = new Paint();
        sinPaint.setStyle(Paint.Style.FILL);
        sinPaint.setColor(Color.parseColor("#1FF421"));
        sinPaint.setAntiAlias(true);
        sinPaint.setStrokeWidth(2);
        for (int i = 0; i < 360; i++) {
            double x = Sin(i);//获取sin值
            double y = Sin(i + 1);
            canvas.drawLine( (40 + (float) i * (float) ((320 * 1.0) / 360)), (float) (230 - x * 80), (40 + (float) (i + 1) * (float) ((320 * 1.0) / 360)), (float) (230 - y * 80), sinPaint);
        }
    }

    public double Sin(int i) {
        double result = 0;
        //在这里我是写sin函数，其实可以用cos，tan等函数的，不信大家尽管试试
//result = Math.cos(i * Math.PI / 180);
        result = Math.sin(i * Math.PI / 180);
//result = Math.tan(i * Math.PI / 180);
        return result;
    }
}
