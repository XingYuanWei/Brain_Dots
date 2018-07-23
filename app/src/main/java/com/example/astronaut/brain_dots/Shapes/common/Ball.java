package com.example.astronaut.brain_dots.Shapes.common;

/*
 *Created by 魏兴源 on 2018-07-08
 *Time at 22:19
 *
 */

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.Constant;

import org.jbox2d.dynamics.Body;

/**
 * 该类表示一个小球，可以表示"脑点子"游戏中那两个一红一蓝的小球
 * 小球特有属性有它的半径
 */

public class Ball extends RigidBodyShapes {
    private float radius = 0;
    public Ball(Body body,float radius,int color){
        super(body,color);
        this.radius = radius;
    }
    @Override
    public void drawBodySelf(Canvas canvas, Paint paint) {
        //设置画笔的颜色
        paint.setColor(bodyColor & 0x8CFFFFFF);
        //获取现实世界里刚体的坐标
        float x = rigidBody.getPosition().x * Constant.RATE;
        float y = rigidBody.getPosition().y * Constant.RATE;
        //画圆
        canvas.drawCircle(x,y,radius,paint);
        paint.reset();
    }
}
