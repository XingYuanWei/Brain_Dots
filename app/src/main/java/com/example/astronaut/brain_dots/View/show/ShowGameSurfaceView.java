package com.example.astronaut.brain_dots.View.show;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


import com.example.astronaut.brain_dots.Activities.MainActivity;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.ColorUtil;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.View.useFrames.RefreshFrame;


import java.util.ArrayList;
import java.util.List;



/*
 *Created by 魏兴源 on 2018-07-09
 *Time at 0:24
 *
 */

@SuppressLint("ViewConstructor")
public class ShowGameSurfaceView extends GLSurfaceView implements SurfaceHolder.Callback {
    private Paint paintPath;
    private Path path;
    private float downX, downY;
    private float currtX, currtY;

    private float preX, preY;
    private List<DrawPath> drawPathList;

    //主Activity
    public MainActivity activity;
    //画笔
    Paint paint;
    //刷帧线程
    RefreshFrame refreshThread;

    public ShowGameSurfaceView(MainActivity activity) {
        super(activity);
        this.activity = activity;
        //设置生命周期回调接口的实现者
        this.getHolder().addCallback(this);
        paint = new Paint();
        //坑锯齿
        paint.setAntiAlias(true);

        drawPathList = new ArrayList<>();
        initPaint();
    }

    private void initPaint() {
        paintPath = new Paint();
        //抗锯齿
        paintPath.setAntiAlias(true);
        paintPath.setStrokeWidth(10);
        paintPath.setStyle(Paint.Style.STROKE);
        paintPath.setStrokeJoin(Paint.Join.ROUND);
        paintPath.setStrokeCap(Paint.Cap.ROUND);// 圆滑
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        if (drawPathList != null && !drawPathList.isEmpty()) {
            for (DrawPath drawPath : drawPathList) {
                if (drawPath.path != null) {
                    canvas.drawPath(drawPath.path, drawPath.paint);
                }
            }
        }
    }

    private void drawBody(Canvas canvas) {
        if (canvas == null) {
            Log.d("tag", "onDraw: " + "canvas is null");
            return;
        } else {
            //背景
            canvas.drawARGB(255, 255, 255, 255);
            for (RigidBodyShapes shapes : activity.getShapesList()) {
                shapes.drawBodySelf(canvas, paint);
            }

        }
    }

    //画出背景
    private void drawBackground(Canvas canvas){
        Paint drawBackPaint = new Paint();
        drawBackPaint.setColor(ColorUtil.getSkyBlue());
        drawBackPaint.setStrokeWidth(10f);
        canvas.drawLine(20f,20f,500f,20f,drawBackPaint);
    }

    @SuppressLint("WrongCall")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void repaint() {
        SurfaceHolder holder = this.getHolder();
        Canvas canvas = holder.lockCanvas();
        try {
            synchronized (holder) {
                drawBody(canvas);
                onDraw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                downX = event.getX();
//                downY = event.getY();
//                path = new Path();
//                path.moveTo(downX, downY);
//                DrawPath drawPath = new DrawPath();
//                drawPath.path = path;
//                drawPath.paint = paintPath;
//                drawPathList.add(drawPath);
//                invalidate();
//                currtX = downX;
//                currtY = downY;
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                float moveX = event.getX();
//                float moveY = event.getY();
//                path.quadTo(currtX, currtY, moveX, moveY);
//                invalidate();
//                currtX = moveX;
//                currtY = moveY;
                float moveX = event.getX();
                float moveY = event.getY();
                System.out.println("{" + moveX + "f," + moveY + "f},");
                if (getDistance(downX, downY, moveX, moveY) > Constant.SEGMENT_LENGTH) {

                }
                break;
            case MotionEvent.ACTION_UP:
                startGravityThread();
//                initPaint();
                break;
        }
        return true;
    }

    /**
     * 开启线程的方法
     * 在滑动事件结束后进行开启
     * 达到小球在运动的目的
     */
    private void startGravityThread() {
        //开启线程
        refreshThread = new RefreshFrame(this);
        refreshThread.start();
        Log.d("tag", "ShowGameSurfaceView: " + "start");
    }

    private float getDistance(float pointOneX, float pointOneY, float pointTwoX, float pointTwoY) {
        float dx = pointOneX - pointTwoX;
        float dy = pointOneY - pointTwoY;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 在触摸点的位置上创建一个正方形
     * 触点为正方形的中点
     */
    private void createRectangleInTouch(float pointOneX, float pointOneY, float pointTwoX, float pointTwoY) {
    }

    /**
     * 本意是想在触点的地方连续的画出一系列的小圆形
     * 以代表出一条连续的直线，发现这样的方法并可行
     * 并且时常程序崩溃
     */
    private void createDots(float pointX, float pointY) {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        repaint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
