package com.example.astronaut.brain_dots.Shapes.common;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.astronaut.brain_dots.Shapes.rules.RigidBody;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;
import com.example.astronaut.brain_dots.Utils.Constant;

import org.jbox2d.dynamics.Body;

import java.util.List;


/*
 *Created by 魏兴源 on 2018-08-08
 *Time at 22:08
 *
 */

public class Polygon extends RigidBodyShapes {
    private Path path;
    private float[][] position;
    private int shape = 2;
    public Polygon(Body body, float[][] position, int color){
        super(body,color);
        this.path = new Path();
        this.position = position;
    }
    @Override
    public void drawBodySelf(Canvas canvas, Paint paint) {
        paint.setColor(bodyColor & 0x8CFFFFFF);
        if (rigidBody == null){
            return;
        }
        float x = rigidBody.getPosition().x * Constant.RATE;
        float y = rigidBody.getPosition().y * Constant.RATE;
        float angle = rigidBody.getAngle();
        canvas.save();
        Matrix matrix = new Matrix();
        matrix.setRotate((float)Math.toDegrees(angle),x,y);
        canvas.setMatrix(matrix);
        path.reset();
        path.moveTo(x + position[0][0],y + position[0][1]);
        for (int i = 1;i < position.length;i++){
            path.lineTo(x + position[i][0],y + position[i][1]);
        }
        path.lineTo(x + position[0][0],y + position[0][1]);

        canvas.drawPath(path,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(bodyColor);
        canvas.drawPath(path,paint);
        paint.reset();
        canvas.restore();
    }

    @Override
    public boolean destroySelf(List list, RigidBody body) {
        float x = rigidBody.getPosition().x * Constant.RATE;
        float y = rigidBody.getPosition().y * Constant.RATE;
        if (x > Constant.SCREEN_HEIGHT && y > Constant.SCREEN_WIDTH){
            list.remove(body);
            return true;
        }
        return false;
    }
}
