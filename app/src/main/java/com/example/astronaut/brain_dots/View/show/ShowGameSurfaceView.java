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


import com.example.astronaut.brain_dots.Activities.GameViewActivity;
import com.example.astronaut.brain_dots.Domain.Creator;
import com.example.astronaut.brain_dots.Shapes.common.Ball;
import com.example.astronaut.brain_dots.Shapes.common.Polygon;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Shapes.special.WeldJointWithTwoBody;
import com.example.astronaut.brain_dots.Utils.gameUtils.BackgroundMusicUtil;
import com.example.astronaut.brain_dots.Utils.ColorUtil;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.MathUtil;
import com.example.astronaut.brain_dots.Utils.gameUtils.GameContactListener;
import com.example.astronaut.brain_dots.View.thread.RefreshFrameThread;


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
    //按下的点
    private float downX, downY;
    private float curryX, curryY;
    //计算两点之间距离的前一个点和后一个点
    private float prePointX, prePointY;
    //移动时的点
    private float curryPointX, curryPointY;
    //要绘制线段(由一个个小矩形组成,存的是各个矩形的四个坐标)坐标的集合
    private List<float[][]> positionList = new ArrayList<>();
    //绘制出的线段是每个元图形是一个矩形 下面的集合是用于盛放这些元图形的集合
    private List<Polygon> metaPolygonList = new ArrayList<>();
    //手绘时候显示其轨迹的集合
    private List<DrawPath> drawPathList;
    //主Activity
    public GameViewActivity activity;
    //画笔
    Paint paint;
    //刷帧线程
    RefreshFrameThread refreshThread;
    //多线程是否已打开标记
    boolean flag = false;
    /*
     * 当红蓝小球碰撞成功之后,画出小星星表示庆贺
     * 该集合用于存放PassTwinkle类,并在指定的位置上
     * 画出小星星
     *
     * */
    public List<PassTwinkle> twinkleList;

    public ShowGameSurfaceView(GameViewActivity activity) {
        super(activity);
        this.activity = activity;
        //设置生命周期回调接口的实现者
        this.getHolder().addCallback(this);
        paint = new Paint();
        //坑锯齿
        paint.setAntiAlias(true);
        //初始化手绘时候显示其轨迹的集合
        drawPathList = new ArrayList<>();
        //初始化胜利显示出小星星的集合
        twinkleList = new ArrayList<>();
        //初始化画笔
        initPaint();
        /*
         * 动画线程的标志初始化为true,在红蓝两球相撞时被置为了false
         * 现在要重新设置为true
         * */
        Constant.DRAW_THREAD_FLAG = true;

        //添加碰撞监听器
        GameContactListener contactListener = new GameContactListener(this);
        activity.world.setContactListener(contactListener);
    }

    //初始化画笔
    private void initPaint() {
        paintPath = new Paint();
        //抗锯齿
        paintPath.setAntiAlias(true);
        paintPath.setStrokeWidth(10);
        paintPath.setStyle(Paint.Style.STROKE);
        paintPath.setStrokeJoin(Paint.Join.ROUND);
        // 圆滑
        paintPath.setStrokeCap(Paint.Cap.ROUND);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawBackground(canvas);
        if (drawPathList != null && !drawPathList.isEmpty()) {
            for (DrawPath drawPath : drawPathList) {
                if (drawPath.path != null) {
                    canvas.drawPath(drawPath.path, drawPath.paint);
                }
            }
        }
    }

    //把刚体集合中所有刚体都画出来
    private void drawBody(Canvas canvas) {
        if (canvas == null) {
//            Log.d("tag", "onDraw: " + "canvas is null");
            return;
        } else {
            //背景
            canvas.drawARGB(255, 255, 255, 255);
            for (RigidBodyShapes bodyShapes : activity.shapesList) {
                if (bodyShapes.isLived){
                    bodyShapes.drawBodySelf(canvas, paint);
                }
            }
        }
    }

    private void drawStar(Canvas canvas) {
        if (!twinkleList.isEmpty()) {
            for (PassTwinkle twinkle : twinkleList) {
                twinkle.drawStar(canvas, activity, paint);
            }
        }
    }

    //画出背景
    private void drawBackground(Canvas canvas) {
        Paint drawBackPaint = new Paint();
        drawBackPaint.setColor(ColorUtil.getSkyBlue());
        drawBackPaint.setStrokeWidth(10f);
        canvas.drawLine(20f, 20f, 500f, 20f, drawBackPaint);
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
                drawStar(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("Tag!!", "onTouchEvent: " + Constant.DRAW_THREAD_FLAG);
                if (!Constant.DRAW_THREAD_FLAG) {
                    break;
                }
                //以下执行在界面上涂鸦的逻辑
                downX = event.getX();
                downY = event.getY();
                //把按压下的点赋值给prePointX，prePointY
                prePointX = downX;
                prePointY = downY;
                //手绘出路线的path
                path = new Path();
                path.moveTo(downX, downY);
                DrawPath drawPath = new DrawPath();
                drawPath.path = path;
                drawPath.paint = paintPath;
                drawPathList.add(drawPath);
                invalidate();
                curryX = downX;
                curryY = downY;
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!Constant.DRAW_THREAD_FLAG) {
                    break;
                }
                //涂鸦的逻辑代码
                float moveX = event.getX();
                float moveY = event.getY();
                //测量两点间距离的逻辑代码
                curryPointX = moveX;
                curryPointY = moveY;
                //两点的距离
                float distanceTwoPoint = MathUtil.getDistance(prePointX, prePointY, curryPointX, curryPointY);
                //根据贝塞尔曲线画出运动的轨迹
                path.quadTo(curryX, curryY, moveX, moveY);
                invalidate();
                curryX = moveX;
                curryY = moveY;
                //为了防止效率低下 造成手绘屏幕时候卡顿 把判断语句写这里
                if (distanceTwoPoint >= Constant.segmentLength) {
                    this.positionList.add(MathUtil.getRectPositionsByTwoPoint(prePointX, prePointY, curryPointX, curryPointY, distanceTwoPoint));
                    prePointX = curryPointX;
                    prePointY = curryPointY;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!Constant.DRAW_THREAD_FLAG) {
                    break;
                }
                //清空路径集合
                drawPathList.clear();
                //初始化画笔 使得手绘的路径痕迹消失
                initPaint();
                //如果坐标点位置集合不为空,则按集合中的点创建矩形
                if (!positionList.isEmpty()) {
                    for (float[][] position : positionList) {
//                    for (float[] positionArray : position) {
//                        System.out.println("{" + positionArray[0] + "," + positionArray[1] + "}");
//                    }
                        Polygon polygon = Creator.createPolygon(position, activity.world);
                        metaPolygonList.add(polygon);
                    }
                }
                /**
                 * 如果线程未曾打开,则在第一次滑动后抬起时打开
                 * 并且为了防止有错误 则如果此时手绘出线程的metaPolygonList
                 * 集合不为空才能打开线程
                 *
                 */
                if (!flag && !metaPolygonList.isEmpty()) {
                    startGravityThread();
                }
                //调用方法先将所有手绘的元矩形全部焊接起来为一个整体
                weldRigidBody(metaPolygonList);
                //把元图形集合组合为一条线段后添加到shapesList中画出显示
                activity.shapesList.addAll(metaPolygonList);
                //抬起时先把List清空,不然下次画的时候这个集合里还有上一次的数据
                this.metaPolygonList.clear();
                this.positionList.clear();
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
        refreshThread = new RefreshFrameThread(this);
        refreshThread.start();
        //是否已经打开线程的标记
        flag = true;
        Log.d("tag", "ShowGameSurfaceView: " + "start");
    }

    /**
     * 把一组集合中刚体用WeldJoint焊接起来
     * 就是把一个个小元矩形组合成一个整体
     * 首先焊接的对象必须要两个或两个以上
     */

    private void weldRigidBody(List<Polygon> polygonList) {
        int length = polygonList.size();
        //如果集合的长度小于2,则说明手绘出的线条长度小于1,则无法连接起来
        if (length < 2) {
            return;
        }
        for (int i = 0; i < length; i++) {
            //集合中下一个刚体的索引
            int index = i + 1;
            if (index < length) {
                Polygon polygonOne = polygonList.get(i);
                Polygon polygonTwo = polygonList.get(index);
                if (polygonOne.rigidBody != null && polygonTwo.rigidBody != null) {
                    new WeldJointWithTwoBody(i + "", activity.world, false, polygonOne, polygonTwo, 0,
                            polygonTwo.rigidBody.getPosition(), 0, 0);
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        repaint();
        //画面创建时候打开BGM
        if (SettingDialog.soundFlag) {
            BackgroundMusicUtil.playBackGroundMusic(true);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

}



