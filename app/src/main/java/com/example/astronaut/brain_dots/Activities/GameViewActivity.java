package com.example.astronaut.brain_dots.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.example.astronaut.brain_dots.Bean.LevelBean;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.MapUtil;
import com.example.astronaut.brain_dots.View.show.ShowGameSurfaceView;

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
    //从上个界面发来的intent

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
        //屏幕自适应
        Constant.ScaleSR();
        //重力
        Vec2 gravity = new Vec2(0f, 10.0f);
        //创建世界
        world = new World(gravity);
        //获得对应的地图数据
        String fileName = "Level/" + levelInfo.getLevelName() + ".map";
        //在地图上放置所以游戏组件
        MapUtil.setLevel(this, fileName);
        //创建游戏面板
        ShowGameSurfaceView gameView = new ShowGameSurfaceView(this);
        setContentView(gameView);
    }
}
