package com.example.astronaut.brain_dots.Shapes.rules;

/*
 *Created by 魏兴源 on 2018-07-08
 *Time at 22:08
 *
 */


import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * 该类为一个接口
 * 表示所有刚体具有的共性，只要是刚体，他们就有一些确定的属性和行为(方法)
 * 只要某个类实现了这个接口，就表示他为刚体的一种
 */
public interface RigidBody {
    /**
     * Box2D集成了大量的物理力学和运动学的计算，
     * 并将物理模拟过程封装到类对象中，将对物体的操作，
     * 以简单友好的接口提供给开发者。我们只需要调用引擎中相应的对象或函数，
     * 就可以模拟现实生活中的加速、减速、抛物线运动、万有引力、
     * 碰撞反弹等等各种真实的物理运动
     */

    //在SurfaceView面板中画出自己
    void drawBodySelf(Canvas canvas, Paint paint);
}
