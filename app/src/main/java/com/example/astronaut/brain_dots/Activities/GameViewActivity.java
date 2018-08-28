package com.example.astronaut.brain_dots.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.astronaut.brain_dots.Bean.LevelBean;
import com.example.astronaut.brain_dots.R;
import com.example.astronaut.brain_dots.Shapes.common.Ball;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.gameUtils.BackgroundMusicUtil;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.gameUtils.GameContactListener;
import com.example.astronaut.brain_dots.Utils.gameUtils.MapUtil;
import com.example.astronaut.brain_dots.View.show.ShowGameSurfaceView;
import com.example.astronaut.brain_dots.View.thread.GarbageThread;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;



/*
 *Created by 魏兴源 on 2018-07-28
 *Time at 16:08
 *
 */

@SuppressLint("Registered")
public class GameViewActivity extends AppCompatActivity {
    //世界
    public World world = null;
    //存放刚体的集合
    public List<RigidBodyShapes> shapesList = new ArrayList<>();
    //只存放红蓝两个小球的集合
    public List<Ball> ballList = new ArrayList<>();
    //回收超出屏幕范围外刚体的线程
    GarbageThread garbageThread;
    //存储图片
    public Bitmap[] pictures = new Bitmap[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //接收来自从选关界面来的信息
        Intent intent = getIntent();
        LevelBean levelInfo = (LevelBean) intent.getSerializableExtra("levelData");
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //获取尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (displayMetrics.widthPixels < displayMetrics.heightPixels) {
            Constant.SCREEN_WIDTH = displayMetrics.widthPixels;
            Constant.SCREEN_HEIGHT = displayMetrics.heightPixels;
        } else {
            Constant.SCREEN_WIDTH = displayMetrics.heightPixels;
            Constant.SCREEN_HEIGHT = displayMetrics.widthPixels;
        }
        //在当前类(所要跳转的Activity页面)中设置该Activity的进出场动画
        getWindow().setEnterTransition(new Explode().setDuration(2000));
        getWindow().setExitTransition(new Explode().setDuration(2000));
        //调用初始化图片方法初始化图片 是图片加载进去
        initBitmap(this.getResources());
        //屏幕自适应
        Constant.ScaleSR();
        //重力
        Vec2 gravity = new Vec2(0f, 10.0f);
        //创建世界
        world = new World(gravity);
        //获得对应的地图数据
        String fileName = "Level/" + levelInfo.getLevelName() + ".map";
        Log.e("Tag!!", "onCreate: " + fileName);
        //在地图上放置所有游戏组件
        MapUtil.setLevel(this, fileName);
        //垃圾回收线程
        garbageThread = new GarbageThread(this);
        //开启垃圾回收线程
        garbageThread.start();

//        Rectangle rectangle = Creator.createRectangle(1200, Constant.SCREEN_WIDTH,
//                1200, 20, true, world, ColorUtil.getStaticBodyColor());
//        shapesList.add(rectangle);

//
//        float[][] position1 =
//                {
//                        {1274.0f, 752.0f},
//                        {1375.0f, 746.5f},
//                        {1375.5437f, 756.4852f},
//                        {1274.5437f, 761.9852f},
//                };
//        float[][] position2 =
//                {
//                        {1375.0f, 746.5f},
//                        {1461.0f, 692.0f},
//                        {1466.3529f, 700.4467f},
//                        {1380.3529f, 754.9467f}
//                };
//        float[][] position3 =
//                {
//                        {1461.0f, 692.0f},
//                        {1528.3643f, 617.2714f},
//                        {1535.7919f, 623.96704f},
//                        {1468.4276f, 698.6956f},
//                };
//        float[][] position4 = {
//                {1528.3643f, 617.2714f},
//                {1583.948f, 530.07794f},
//                {1592.3804f, 535.45337f},
//                {1536.7966f, 622.64685f},
//        };
//        float[][] position5 = {
//                {1583.948f, 530.07794f},
//                {1619.0f, 433.0015f},
//                {1628.4056f, 436.39764f},
//                {1593.3536f, 533.4741f},
//        };
//
//        float[][] position6 = {
//                {1619.0f, 433.0015f},
//                {1580.5717f, 340.33362f},
//                {1571.3345f, 344.1642f},
//                {1609.7628f, 436.8321f},
//        };
//        float[][] position7 = {
//                {1580.5717f, 340.33362f},
//                {1489.8202f, 278.73645f},
//                {1484.2042f, 287.01053f},
//                {1574.9557f, 348.6077f},
//        };
//        float[][] position8 = {
//                {1489.8202f, 278.73645f},
//                {1378.0542f, 243.94766f},
//                {1375.0822f, 253.49582f},
//                {1486.8481f, 288.2846f},
//        };
//        float[][] position9 = {
//                {1378.0542f, 243.94766f},
//                {1259.348f, 229.7348f},
//                {1258.1592f, 239.66388f},
//                {1376.8654f, 253.87674f},
//        };
//        float[][] position10 = {
//                {1259.348f, 229.7348f},
//                {1145.2914f, 216.91142f},
//                {1144.1741f, 226.84882f},
//                {1258.2307f, 239.6722f},
//        };
//        float[][] position11 = {
//                {1145.2914f, 216.91142f},
//                {1030.3047f, 206.1186f},
//                {1029.3702f, 216.07484f},
//                {1144.3569f, 226.86766f},
//        };
//        float[][] position12 = {
//                {1030.3047f, 206.1186f},
//                {908.49066f, 202.0f},
//                {908.1528f, 211.9943f},
//                {1029.9668f, 216.1129f},
//        };
//        float[][] position13 = {
//                {908.49066f, 202.0f},
//                {781.6833f, 208.63976f},
//                {782.2062f, 218.62607f},
//                {909.01355f, 211.98631f},
//        };
//        float[][] position14 = {
//                {781.6833f, 208.63976f},
//                {670.81213f, 221.87177f},
//                {671.9972f, 231.8013f},
//                {782.86835f, 218.56929f},
//        };
//        float[][] position15 = {
//                {670.81213f, 221.87177f},
//                {573.91895f, 259.0608f},
//                {577.5022f, 268.39676f},
//                {674.3954f, 231.20772f},
//        };
//        float[][] position16 = {
//                {573.91895f, 259.0608f},
//                {562.9206f, 367.14264f},
//                {572.8692f, 368.155f},
//                {583.86755f, 260.07315f},
//        };
//        float[][] position17 = {
//                {562.9206f, 367.14264f},
//                {621.97f, 451.23996f},
//                {613.78595f, 456.98642f},
//                {554.7366f, 372.8891f},
//        };
//        float[][] position18 = {
//                {621.97f, 451.23996f},
//                {718.597f, 517.2877f},
//                {712.954f, 525.5434f},
//                {616.32697f, 459.49564f},
//        };
//        float[][] position19 = {
//                {718.597f, 517.2877f},
//                {835.2201f, 548.70337f},
//                {832.619f, 558.3592f},
//                {715.9959f, 526.94354f},
//        };
//        float[][] position20 = {
//                {835.2201f, 548.70337f},
//                {941.4217f, 538.2396f},
//                {942.4022f, 548.1914f},
//                {836.2006f, 558.65515f},
//        };
//        float[][] position21 = {
//                {941.4217f, 538.2396f},
//                {1047.1776f, 501.32895f},
//                {1050.4729f, 510.77042f},
//                {944.7169f, 547.6811f}
//        };

//        List<float[][]> floatsList = new ArrayList<>();
//        floatsList.add(position1);
//        floatsList.add(position2);
//        floatsList.add(position3);
//        floatsList.add(position4);
//        floatsList.add(position5);
//        floatsList.add(position6);
//        floatsList.add(position7);
//        floatsList.add(position8);
//        floatsList.add(position9);
//        floatsList.add(position10);
//        floatsList.add(position11);
//        floatsList.add(position12);
//        floatsList.add(position13);
//        floatsList.add(position14);
//        floatsList.add(position15);
//        floatsList.add(position16);
//        floatsList.add(position17);
//        floatsList.add(position18);
//        floatsList.add(position19);
//        floatsList.add(position20);
//        floatsList.add(position21);
//        for (float[][] posi : floatsList){
//            Polygon polygon = Creator.createPolygon(posi,world);
////            shapesList.add(polygon);
//        }

        //创建游戏面板
        ShowGameSurfaceView gameView = new ShowGameSurfaceView(this);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //退出界面时候 音乐停止
        BackgroundMusicUtil.playBackGroundMusic(false);
    }

    //初始化图片
    private void initBitmap(Resources resources) {
        pictures[0] = BitmapFactory.decodeResource(resources, R.drawable.star);
        pictures[1] = BitmapFactory.decodeResource(resources,R.drawable.failed);
    }
}
