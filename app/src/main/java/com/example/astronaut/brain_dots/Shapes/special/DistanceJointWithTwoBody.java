package com.example.astronaut.brain_dots.Shapes.special;

/*
 *Created by 魏兴源 on 2018-07-14
 *Time at 0:33
 *
 */

import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;


/**
 *  距离关节，用于连接两个刚体
 *  距离关节是指两个刚体之间保持着固定不变的距离，
 *  同时刚体可以任意运动和旋转。
 *  这里说的保持 固定不变的距离，
 *  是指关联着的两个刚体的锚点之间的距离
 *
 * */
public class DistanceJointWithTwoBody {
    World world;//物理世界对象
    DistanceJoint dj;//距离关节对象

    public DistanceJointWithTwoBody
            (
                    String id,//id
                    World world,//物理世界
                    boolean collideConnected,//是否允许碰撞
                    RigidBodyShapes poA,//刚体A
                    RigidBodyShapes poB,//刚体B
                    Vec2 anchorA,//锚点
                    Vec2 anchorB,//锚点
                    float frequencyHz,//为0表示禁柔度
                    float dampingRatio// 阻尼系数.
            ) {
        this.world = world;
        DistanceJointDef djd = new DistanceJointDef();//声明关节描述对象
        djd.collideConnected = collideConnected;//给是否允许碰撞标志赋值
        djd.userData = id;//给关节描述的用户数据赋予关节id
        djd.initialize(poA.rigidBody, poB.rigidBody, anchorA, anchorB);//调用关节的初始化方法
        djd.frequencyHz = frequencyHz;//设置关节频率
        djd.dampingRatio = dampingRatio;//设置关节阻尼系数
        dj = (DistanceJoint) world.createJoint(djd);//在物理世界添加距离关节
    }
}
