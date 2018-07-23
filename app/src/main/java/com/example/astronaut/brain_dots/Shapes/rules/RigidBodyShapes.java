package com.example.astronaut.brain_dots.Shapes.rules;

/*
 *Created by 魏兴源 on 2018-07-08
 *Time at 22:28
 *
 */

import android.graphics.Canvas;
import android.graphics.Paint;

import org.jbox2d.dynamics.Body;

public abstract class RigidBodyShapes implements RigidBody {
    //在物理引擎中Body表示一个刚体
    public Body rigidBody = null;
    //刚体的颜色
    public int bodyColor = -1;

    public RigidBodyShapes(Body body,int color){
        this.rigidBody = body;
        this.bodyColor = color;
    }
    @Override
    public abstract void drawBodySelf(Canvas canvas, Paint paint);
}
