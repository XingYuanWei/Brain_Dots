package com.example.astronaut.brain_dots.View.gameShow;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.example.astronaut.brain_dots.R;
import com.example.astronaut.brain_dots.Utils.ColorUtil;
import com.example.astronaut.brain_dots.Utils.Constant;

import java.util.ArrayList;
import java.util.List;


/*
 *Created by 魏兴源 on 2018-08-29
 *Time at 22:17
 *
 */

/**
 * 该类用于显示游戏结束(通过或失败)的界面
 * 在该界面上,可以点击重新开始按钮,也可以
 * 点击下一关按钮
 */
public class ShowResultSurfaceView extends GLSurfaceView implements Runnable, SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private boolean runFlag = false;
    protected Resources resources;

    /*
     * 当红蓝小球碰撞成功之后,画出小星星表示庆贺
     * 该集合用于存放PassTwinkle类,并在指定的位置上
     * 画出小星星
     *
     * */

    private List<PassTwinkle> twinkleList = new ArrayList<>();
    //存放图片的数组
    Bitmap[] pictures = new Bitmap[5];

    public ShowResultSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShowResultSurfaceView(Context context) {
        super(context);
        init();
    }

    //初始化
    private void init() {
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        resources = this.getResources();
        //初始化图片
        initPictures(resources);
        //初始化画笔
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        this.setKeepScreenOn(true);
    }

    //把要画出的图片载入
    private void initPictures(Resources resources) {
        pictures[0] = BitmapFactory.decodeResource(resources, R.drawable.star);
        pictures[1] = BitmapFactory.decodeResource(resources, R.drawable.failed);
        pictures[2] = BitmapFactory.decodeResource(resources, R.drawable.background);
        pictures[3] = BitmapFactory.decodeResource(resources, R.drawable.pass);
    }

    //游戏通关显示出星星图标
    private void drawStar(Canvas canvas) {
        //如果此时游戏胜利,画出星星
        if (Constant.STATE == 1) {
            //如果此时集合为空,则表示集合中没有PassTwinkle对象,则向其中添加
            if (twinkleList.isEmpty()) {
                /*
                 * 在集合中添加三个星星,添加之前先清空上次一个可能存在的对象
                 * 如果不清空集合可能会导致添加很多PassTwinkle对象,存在可能
                 * 画面重叠,或者影响效率
                 *
                 * */
                /*
                 * 下面这段代码算法有误 记得更改！！！！！！！！！
                 * */
                twinkleList.clear();
                float halfHeight = Constant.RECTANGLE_HEIGHT / 2 * 150;
                //六分之一的宽
                float one_sixWidth = Constant.RECTANGLE_WIDTH / 6;
                twinkleList.add(new PassTwinkle(halfHeight, one_sixWidth));
                float offsetX = 320;
                float offsetY = 150;
                twinkleList.add(new PassTwinkle(halfHeight - offsetX, one_sixWidth + offsetY));
                twinkleList.add(new PassTwinkle(halfHeight + offsetX, one_sixWidth + offsetY));
            }
            for (PassTwinkle twinkle : twinkleList) {
                twinkle.drawStar(canvas, this, paint);
            }
            if (canvas != null) {
                //关卡通过图标
                canvas.drawBitmap(pictures[3], Constant.SCREEN_HEIGHT / 2 - 320, Constant.SCREEN_WIDTH / 7, paint);
            }
        }
    }

    //游戏失败显示出图标
    private void drawUnSmile(Canvas canvas) {
        //如果此时游戏状态STATE为false则表示为通关失败,则画出失败的图标
        if (Constant.STATE == 0) {
            /*
             * (Constant.SCREEN_HEIGHT / 2)这个值使得图标太过右边,所以减去300,这是所要画的图像的X位置
             * Constant.SCREEN_WIDTH / 10这是所要画的图像的Y位置
             *
             * */
            new UnPassIcon(Constant.SCREEN_HEIGHT / 2 - 350, Constant.SCREEN_WIDTH / 10).drawUnSmile(canvas, this, paint);
        }
    }

    //画出背景
    private void drawBackground(Canvas canvas) {
        if (canvas != null) {
            //主背景色调
            canvas.drawARGB(255, 255, 255, 255);
            Paint drawBackPaint = new Paint();
            drawBackPaint.setColor(ColorUtil.getSkyBlue());
            drawBackPaint.setStrokeWidth(0.5f);
            canvas.drawBitmap(pictures[2], 0, 0, paint);
//        int num =
            for (int i = 0; i < 10; i++) {
                canvas.drawLine(0f, i * 100, Constant.SCREEN_HEIGHT, i * 100, drawBackPaint);
                canvas.drawLine(i * 100, 0, i * 100, Constant.SCREEN_WIDTH, drawBackPaint);
            }
        }
    }


    //所有画图方法都在这里调用
    private void repaint() {
        //锁定画布
        Canvas canvas = surfaceHolder.lockCanvas();
        try {
            synchronized (surfaceHolder) {
                //要先调用画背景方法,否则可能其他会被覆盖
                drawBackground(canvas);
                drawStar(canvas);
                drawUnSmile(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                //释放画布
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void run() {
        while (runFlag) {
            repaint();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        runFlag = true;
        Thread resultViewThread = new Thread(this);
        resultViewThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        super.surfaceChanged(holder, format, w, h);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (holder != null) {
//            super.surfaceDestroyed(holder);
//        }
        runFlag = false;
    }
}
