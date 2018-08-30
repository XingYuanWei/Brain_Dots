package com.example.astronaut.brain_dots.Shapes.common;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.astronaut.brain_dots.Shapes.rules.RigidBody;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.Constant;

import org.jbox2d.dynamics.Body;

import java.util.List;


/*
 *Created by 魏兴源 on 2018-07-11
 *Time at 10:06
 *
 */

public class Line extends RigidBodyShapes {
    //线的长度
    private float length;
    private float startX, startY;
    private float endX, endY;
//    private Path path;
//    private Paint pathPaint;
//    private List<DrawPath> drawPathsList;

    /**
     * 初始化画笔
     */
//    private void initPaint(){
//        pathPaint = new Paint();
//        //抗锯齿
//        pathPaint.setAntiAlias(true);
//        pathPaint.setStrokeWidth(10);
//        pathPaint.setStyle(Paint.Style.STROKE);
//        pathPaint.setStrokeJoin(Paint.Join.ROUND);
//        pathPaint.setStrokeCap(Paint.Cap.ROUND);// 圆滑
//    }
    public Line(Body body, int color,float length) {
        super(body, color);
        this.length = length;
//        drawPathsList = new ArrayList<>();
//        initPaint();
//        this.startX = startX;
//        this.startY = startY;
//        this.endX = endX;
//        this.endY = endY;
    }

    @Override
    public void drawBodySelf(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        float x = rigidBody.getPosition().x;
        float y = rigidBody.getPosition().y;
        canvas.drawLine(x - length, y, x + length, y, paint);

//        canvas.drawLine(500,400,900,400,paint);
    }

    @Override
    public boolean destroySelf(List list, RigidBody body) {
        float x = rigidBody.getPosition().x * Constant.RATE;
        float y = rigidBody.getPosition().y * Constant.RATE;
        if (x > Constant.SCREEN_HEIGHT && y > Constant.SCREEN_WIDTH){
            if (!list.isEmpty()){
                list.remove(body);
                return true;
            }
        }
        return false;
    }
}
