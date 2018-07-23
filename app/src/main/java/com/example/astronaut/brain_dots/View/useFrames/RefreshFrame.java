package com.example.astronaut.brain_dots.View.useFrames;

/*
 *Created by 魏兴源 on 2018-07-09
 *Time at 0:28
 *
 */

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.View.show.ShowGameSurfaceView;

/**
 *  该类用于刷新各个对象中的数据，比如坐标信息，尺寸信息等
 *  为了将来可能需要返回刷新后的结果，使用Callable接口实现多线程
 *
 * */

public class RefreshFrame extends Thread {
   private ShowGameSurfaceView gameView;
    public RefreshFrame(ShowGameSurfaceView gameView){
        this.gameView = gameView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        while (Constant.DRAW_THREAD_FLAG){
            gameView.activity.getWorld().step(Constant.TIME_STEP,Constant.ITERATIONS,Constant.ITERATIONS);
//            Log.d("tag", "run: X" + gameView.activity.getShapesList().get(0).rigidBody.getPosition().x);
//            Log.d("tag", "run: Y" + gameView.activity.getShapesList().get(0).rigidBody.getPosition().y);
            gameView.repaint();
        }
    }
}































