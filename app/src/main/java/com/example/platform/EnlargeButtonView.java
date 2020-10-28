package com.example.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EnlargeButtonView extends View {

    public EnlargeButtonView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    private class Point{
        float x;
        float y;
    }

    public boolean enlarged=false;

    private Path path = new Path();

    private Paint paint=new Paint();    //创建画笔

    private int color = 0xffff0099;

    private Point middlePoint=new Point();

    private Point topLeft=new Point();
    private Point topRight=new Point();
    private Point bottomLeft=new Point();
    private Point bottomRight=new Point();

    private Point middleTopLeft=new Point();
    private Point middleTopRight=new Point();
    private Point middleLeftTop=new Point();
    private Point middleLeftBottom=new Point();
    private Point middleRightTop=new Point();
    private Point middleRightBottom=new Point();
    private Point middleBottomLeft=new Point();
    private Point middleBottomRight=new Point();

    private Point insideTopLeft=new Point();
    private Point insideTopRight=new Point();
    private Point insideBottomLeft=new Point();
    private Point insideBottomRight=new Point();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float viewHeight = getMeasuredHeight();
        float viewWidth = getMeasuredWidth();

        float distance= viewHeight<viewWidth ? viewHeight/4 : viewWidth/4;

        //获取控件中心点
        middlePoint.x=viewWidth/2;
        middlePoint.y=viewHeight/2;

        //设置四个外顶点
        topLeft.x=middlePoint.x-distance;
        topLeft.y=middlePoint.y-distance;
        topRight.x=middlePoint.x+distance;
        topRight.y=middlePoint.y-distance;
        bottomLeft.x=middlePoint.x-distance;
        bottomLeft.y=middlePoint.y+distance;
        bottomRight.x=middlePoint.x+distance;
        bottomRight.y=middlePoint.y+distance;

        //设置四个内顶点
        middleTopLeft.x=middlePoint.x-distance/3;
        middleTopLeft.y=middlePoint.y-distance;
        middleLeftTop.x=middlePoint.x-distance;
        middleLeftTop.y=middlePoint.y-distance/3;
        middleTopRight.x=middlePoint.x+distance/3;
        middleTopRight.y=middlePoint.y-distance;
        middleRightTop.x=middlePoint.x+distance;
        middleRightTop.y=middlePoint.y-distance/3;
        middleBottomLeft.x=middlePoint.x-distance/3;
        middleBottomLeft.y=middlePoint.y+distance;
        middleLeftBottom.x=middlePoint.x-distance;
        middleLeftBottom.y=middlePoint.y+distance/3;
        middleBottomRight.x=middlePoint.x+distance/3;
        middleBottomRight.y=middlePoint.y+distance;
        middleRightBottom.x=middlePoint.x+distance;
        middleRightBottom.y=middlePoint.y+distance/3;

        insideTopLeft.x=middlePoint.x-distance/3;
        insideTopLeft.y=middlePoint.y-distance/3;
        insideTopRight.x=middlePoint.x+distance/3;
        insideTopRight.y=middlePoint.y-distance/3;
        insideBottomLeft.x=middlePoint.x-distance/3;
        insideBottomLeft.y=middlePoint.y+distance/3;
        insideBottomRight.x=middlePoint.x+distance/3;
        insideBottomRight.y=middlePoint.y+distance/3;


        paint.setColor(color);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(enlarged==false){
            path.reset();
            path.moveTo(middleTopLeft.x,middleTopLeft.y);
            path.lineTo(topLeft.x, topLeft.y);
            path.lineTo(middleLeftTop.x,middleLeftTop.y);

            path.moveTo(middleRightTop.x,middleRightTop.y);
            path.lineTo(topRight.x,topRight.y);
            path.lineTo(middleTopRight.x,middleTopRight.y);

            path.moveTo(middleLeftBottom.x,middleLeftBottom.y);
            path.lineTo(bottomLeft.x,bottomLeft.y);
            path.lineTo(middleBottomLeft.x,middleBottomLeft.y);

            path.moveTo(middleBottomRight.x,middleBottomRight.y);
            path.lineTo(bottomRight.x,bottomRight.y);
            path.lineTo(middleRightBottom.x,middleRightBottom.y);
            canvas.drawPath(path, paint);
        }else {
            path.reset();
            path.moveTo(middleTopLeft.x,middleTopLeft.y);
            path.lineTo(insideTopLeft.x, insideTopLeft.y);
            path.lineTo(middleLeftTop.x,middleLeftTop.y);

            path.moveTo(middleRightTop.x,middleRightTop.y);
            path.lineTo(insideTopRight.x,insideTopRight.y);
            path.lineTo(middleTopRight.x,middleTopRight.y);

            path.moveTo(middleLeftBottom.x,middleLeftBottom.y);
            path.lineTo(insideBottomLeft.x,insideBottomLeft.y);
            path.lineTo(middleBottomLeft.x,middleBottomLeft.y);

            path.moveTo(middleBottomRight.x,middleBottomRight.y);
            path.lineTo(insideBottomRight.x,insideBottomRight.y);
            path.lineTo(middleRightBottom.x,middleRightBottom.y);
            canvas.drawPath(path, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
