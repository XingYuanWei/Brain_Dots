package com.example.astronaut.brain_dots.View.thread;

/*
 *Created by 魏兴源 on 2018-07-09
 *Time at 0:28
 *
 */

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.View.gameShow.ShowGameSurfaceView;

/**
 * 该类用于刷新各个对象中的数据，比如坐标信息，尺寸信息等
 * 为了将来可能需要返回刷新后的结果，使用Callable接口实现多线程
 */

public class RefreshFrameThread extends Thread {
    private ShowGameSurfaceView gameView;

    public RefreshFrameThread(ShowGameSurfaceView gameView) {
        this.gameView = gameView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {//Constant.DRAW_THREAD_FLAG
        while (true) {
            /*
             *
             * 这样写的好处是当Constant.DRAW_THREAD_FLAG为false的时候
             * 模拟停止,但是线程没有停止,可以继续在上面写动画.是否有其他
             * 逻辑上的错误暂时未知.
             *
             * */
            if (Constant.DRAW_THREAD_FLAG) {
//                Log.e("Tag!!", "run: " + "step" );
                gameView.activity.world.step(Constant.TIME_STEP, Constant.ITERATIONS, Constant.ITERATIONS);
            }
//            Log.d("tag", "run: X" + gameView.activity.getShapesList().get(0).rigidBody.getPosition().x);
//            Log.d("tag", "run: Y" + gameView.activity.getShapesList().get(0).rigidBody.getPosition().y);
            gameView.repaint();
//            Log.e("Tag!!", "run: " + "running" );
        }
    }
}































