package com.example.astronaut.brain_dots.Shapes.common;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.astronaut.brain_dots.Shapes.rules.RigidBody;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;

import org.jbox2d.dynamics.Body;

import java.util.List;


/*
 *Created by 魏兴源 on 2018-07-28
 *Time at 22:59
 *
 */

public class Triangle extends RigidBodyShapes {
    private int shape = 3;
    public Triangle(Body body, int color) {
        super(body, color);
    }

    @Override
    public void drawBodySelf(Canvas canvas, Paint paint) {

    }

    @Override
    public boolean destroySelf(List list, RigidBody body) {
        return false;
    }
}
