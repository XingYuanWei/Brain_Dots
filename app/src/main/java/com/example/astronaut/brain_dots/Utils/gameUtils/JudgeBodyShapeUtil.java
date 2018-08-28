package com.example.astronaut.brain_dots.Utils.gameUtils;

/*
 *Created by 魏兴源 on 2018-08-27
 *Time at 10:45
 *
 */


import com.example.astronaut.brain_dots.Shapes.common.Ball;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBody;
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;

import org.jbox2d.dynamics.Body;

import java.util.List;

/**
 * 此类用于判断碰撞的的物体的形状,如果是两个小球碰撞
 * 则停止运动线程,画面静止.
 */
public class JudgeBodyShapeUtil {
    public static void judgeBall(RigidBodyShapes body1, RigidBody body2, List<RigidBodyShapes> bodyList, List<RigidBodyShapes> deleteBodyList) {
        //遍历所有物体集合
        for (RigidBodyShapes bodyShape : bodyList){
            if (body1 instanceof Ball && body2 instanceof Ball){

            }
        }
    }
}
