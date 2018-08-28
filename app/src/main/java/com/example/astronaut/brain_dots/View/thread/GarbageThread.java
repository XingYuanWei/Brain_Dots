package com.example.astronaut.brain_dots.View.thread;

/*
 *Created by 魏兴源 on 2018-08-25
 *Time at 16:41
 *
 */

import android.util.Log;

import com.example.astronaut.brain_dots.Activities.GameViewActivity;
import com.example.astronaut.brain_dots.Shapes.common.Ball;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBody;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.Constant;

import java.util.List;

/**
 * 此线程用于回收刚体集合中的废弃刚体,避免集合内容过大臃肿
 * 影响整体画面显示流畅,但是在集合遍历的过程中,如果对集合进行更改
 * 会抛出 java.util.ConcurrentModificationException异常,要避免此
 * 问题.并且这是多线程环境下的解决方法.
 */
public class GarbageThread extends Thread {
    private final List<RigidBodyShapes> rigidBodyList;
    private final List<Ball> ballList;

    public GarbageThread(GameViewActivity activity) {
        this.rigidBodyList = activity.shapesList;
        this.ballList = activity.ballList;
    }

    @Override
    public void run() {
        while (true) {
            if (ballList.size() != 2){
                continue;
            }
            Ball ballOne = ballList.get(0);
            Ball ballTwo = ballList.get(1);
            float oneX = ballOne.rigidBody.getPosition().x * Constant.RATE;
            float oneY = ballOne.rigidBody.getPosition().y * Constant.RATE;
            float twoX = ballTwo.rigidBody.getPosition().x * Constant.RATE;
            float twoY = ballTwo.rigidBody.getPosition().y * Constant.RATE;
//            Log.e("Tag!!", "GarbageRun:" + oneX + ":" + oneY + ":" + twoX + ":" + twoY);
        }
    }
}
