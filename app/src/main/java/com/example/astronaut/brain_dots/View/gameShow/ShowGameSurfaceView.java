package com.example.astronaut.brain_dots.View.gameShow;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.astronaut.brain_dots.R;
import com.example.astronaut.brain_dots.Shapes.common.Polygon;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Shapes.special.WeldJointWithTwoBody;
import com.example.astronaut.brain_dots.Utils.BitmapUtil;
import com.example.astronaut.brain_dots.Utils.gameUtils.BackgroundMusicUtil;
import com.example.astronaut.brain_dots.Utils.ColorUtil;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.MathUtil;
import com.example.astronaut.brain_dots.View.componentShow.SettingDialog;
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
    //Bitmap数组
    public Bitmap[] obstacleBitmaps;

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

        //初始化画笔
        initPaint();
        //初始化图片
        initBitmap(this.getResources());
        /*
         * 动画线程的标志初始化为true,在红蓝两球相撞时被置为了false
         * 现在要重新设置为true
         * */
        Constant.DRAW_THREAD_FLAG = true;
        //同样的也要把游戏输赢状态重置,-1为输赢未知状态
        Constant.STATE = -1;

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

    //初始化Bitmap数组
    private void initBitmap(Resources resources) {
        obstacleBitmaps = null;
        obstacleBitmaps = new Bitmap[3];
        obstacleBitmaps[0] = BitmapFactory.decodeResource(resources, R.drawable.web_obstacle);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    //根据手滑动的路径,画出轨迹
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawPathList != null && !drawPathList.isEmpty()) {
            for (DrawPath drawPath : drawPathList) {
                if (drawPath.path != null) {
                    canvas.drawPath(drawPath.path, drawPath.paint);
                }
            }
        }
    }

    //画出网格阻碍物集合
    private void drawWebObstacle(Canvas canvas) {
        //如果放置网格阻碍物的集合不为空
        if (activity.webCantTouchAreaList != null) {
            //遍历集合并画出
            for (WebCantTouchArea webCantTouchArea : activity.webCantTouchAreaList) {
                //更改图片大小
                Bitmap drawBitmap = BitmapUtil.resizeBitmap(webCantTouchArea.getWidth(), webCantTouchArea.getHeight(), obstacleBitmaps[0]);
                webCantTouchArea.drawWebArea(canvas, drawBitmap, paint);
            }
        }
    }

    //把刚体集合中所有刚体都画出来
    private void drawBody(Canvas canvas) {
        if (canvas != null) {
            for (RigidBodyShapes bodyShapes : activity.shapesList) {
                if (bodyShapes.isLived) {
                    bodyShapes.drawBodySelf(canvas, paint);
                }
            }
        }
    }

    //画出背景
    private void drawBackground(Canvas canvas) {
        if (canvas != null) {
            //主背景色调
            canvas.drawARGB(255, 255, 255, 255);
            //画背景的画笔
            Paint drawBackPaint = new Paint();
            //设置画笔颜色
            drawBackPaint.setColor(ColorUtil.getSkyBlue());
            drawBackPaint.setStrokeWidth(1f);
            for (int i = 0; i < 10; i++) {
//            canvas.drawLine(0,i * 100,Constant.SCREEN_HEIGHT,i * 100,drawBackPaint);
//            canvas.drawLine(i * 100,0,i * 100,Constant.SCREEN_WIDTH,drawBackPaint);
            }
        }
    }

    @SuppressLint("WrongCall")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void repaint() {
        SurfaceHolder holder = this.getHolder();
        Canvas canvas = holder.lockCanvas();
        try {
            synchronized (holder) {
                //先调用
                drawBackground(canvas);
                drawBody(canvas);
                onDraw(canvas);
                drawWebObstacle(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }


    private boolean isCantTouch(List<WebCantTouchArea> list, MotionEvent event) {
        if (list == null) {
            return false;
        } else {
            for (WebCantTouchArea webCantTouchArea : activity.webCantTouchAreaList) {
                float touchX = event.getX();
                float touchY = event.getY();
                float areaWidth = webCantTouchArea.getStartX() + webCantTouchArea.getWidth();
                float areaHeight = webCantTouchArea.getStartY() + webCantTouchArea.getHeight();
                if (touchX > webCantTouchArea.getStartX() && touchX < areaWidth &&
                        touchY > webCantTouchArea.getStartY() && touchY < areaHeight) {
                    Log.e("Tag!!", "这里触摸无效!");
                    return true;
                }
            }
        }
        return false;
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
                //调用方法判断该触摸点区域是否为不可绘画区域
                if (isCantTouch(activity.webCantTouchAreaList, event)) {
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
                //调用方法判断该触摸点区域是否为不可绘画区域
                if (isCantTouch(activity.webCantTouchAreaList, event)) {
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
                if (path != null) {
                    path.quadTo(curryX, curryY, moveX, moveY);
                }
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
                //调用方法判断该触摸点区域是否为不可绘画区域
                if (isCantTouch(activity.webCantTouchAreaList, event)) {
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
                if (!metaPolygonList.isEmpty() && Constant.IS_NEW_THREAD) {
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
        refreshThread = null;
        System.gc();
        //开启线程
        refreshThread = new RefreshFrameThread(this);
        refreshThread.start();
        //是否已经打开线程的标记
        Constant.IS_NEW_THREAD = false;
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
                //前一个刚体和后一个刚体,两两连接
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