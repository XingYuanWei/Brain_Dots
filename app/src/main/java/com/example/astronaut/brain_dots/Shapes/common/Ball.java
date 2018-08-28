package com.example.astronaut.brain_dots.Shapes.common;

/*
 *Created by 魏兴源 on 2018-07-08
 *Time at 22:19
 *
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.astronaut.brain_dots.Shapes.rules.RigidBody;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.gameUtils.BackgroundMusicUtil;
import com.example.astronaut.brain_dots.View.show.SettingDialog;

import org.jbox2d.dynamics.Body;

import java.util.List;

/**
 * 该类表示一个小球，可以表示"脑点子"游戏中那两个一红一蓝的小球
 * 小球特有属性有它的半径
 */

public class Ball extends RigidBodyShapes {
    /*
     * 0 为球形状
     * 1 为矩形
     * 2 为多边形(包括矩形)
     * */
    private int shape = 0;
    private float radius = 0;

    public Ball(Body body, float radius, int color) {
        super(body, color);
        this.radius = radius;
    }

    @Override
    public void drawBodySelf(Canvas canvas, Paint paint) {
        //设置画笔的颜色
        paint.setColor(bodyColor & 0x8CFFFFFF);
        //获取现实世界里刚体的坐标
        float x = rigidBody.getPosition().x * Constant.RATE;
        float y = rigidBody.getPosition().y * Constant.RATE;
        Log.e("Tag!!", "drawBodySelf: " + "X:" +x + "Y:" + y);
        /*
         * 如果此时x坐标或y坐标大于了竖屏时的宽和高,或x小于0,重力向下(y不会小于0)
         * 则把模拟线程停止(即把DRAW_THREAD_FLAGd置为false)
         * 并且此时播放失败的音效(如果此时用户打开音效的话)
         *
         * */
        if (x < 0 || x > Constant.SCREEN_HEIGHT || y > Constant.SCREEN_WIDTH) {
            if (SettingDialog.soundEffectFlag) {
                BackgroundMusicUtil.playSound(3, 1);
            }
            Constant.DRAW_THREAD_FLAG = false;
        }
        //画圆
        canvas.drawCircle(x, y, radius, paint);
        paint.reset();
    }

    @Override
    public boolean destroySelf(List list, RigidBody body) {
        //获取坐标
        float x = rigidBody.getPosition().x * Constant.RATE;
        float y = rigidBody.getPosition().y * Constant.RATE;

        Log.e("Tag!!", "drawBodySelf: " + x + ":" + y);
        if (x > Constant.SCREEN_HEIGHT || y > Constant.SCREEN_WIDTH) {
            Constant.DRAW_THREAD_FLAG = false;
        }
        return false;
    }


}
