package com.example.astronaut.brain_dots.Shapes.common;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.Constant;

import org.jbox2d.dynamics.Body;


/*
 *Created by 魏兴源 on 2018-07-08
 *Time at 23:10
 *
 */


/**
 * 表示一个矩形
 * 矩形特有的属性为它的长和高
 * <p>
 * 但是这里声明的矩形的构造函数是他的半宽和半长
 * 因为在画矩形的时候调用方法为drawReact(left,top,right,bottom);
 * 为了把刚体的左上角的坐标作为原点坐标而进行的转换
 */
public class Rectangle extends RigidBodyShapes {

    //声明半宽和半长
    private float halfWidth;
    private float halfHeight;

    //倾斜角
    private float angle = 0;
    public Rectangle(Body body, int color, float halfWidth, float halfHeight) {
        super(body, color);
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;
    }

    public Rectangle(Body body, int color, float halfWidth, float halfHeight,float angle) {
        super(body, color);
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;
        this.angle = angle;
    }

    @Override
    public void drawBodySelf(Canvas canvas, Paint paint) {
//        paint.setColor(Color.BLACK);
        paint.setColor(bodyColor & 0x8CFFFFFF);//设置画笔颜色
        float x = rigidBody.getPosition().x * Constant.RATE;//获得现实世界里刚体的X坐标
        float y = rigidBody.getPosition().y * Constant.RATE;//获得现Con实世界里刚体的Y坐标
//        float angle = rigidBody.getAngle();//获得刚体的旋转角度
        canvas.save();//保存画布的状态
        Matrix m1 = new Matrix();//创建矩阵
        m1.setRotate(angle, x, y);//设置变换矩阵
        canvas.setMatrix(m1);//根据矩阵设置画布状态
        canvas.drawRect(x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight, paint);//画矩形
        paint.reset();//重置画笔
        canvas.restore();//取出保存的状态
    }
}
