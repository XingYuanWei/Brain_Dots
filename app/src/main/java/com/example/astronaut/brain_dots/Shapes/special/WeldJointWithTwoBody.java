package com.example.astronaut.brain_dots.Shapes.special;

/*
 *Created by 魏兴源 on 2018-07-14
 *Time at 10:12
 *
 */

import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

public class WeldJointWithTwoBody {
    private World world;//声明物理世界引用
    private WeldJoint weldJoint;//声明焊接关节引用


    /**
     * @param id               关节id
     * @param world            物理世界对象
     * @param collideConnected 是否允许两个刚体碰撞
     * @param bodyA            刚体A
     * @param bodyB            刚体B
     * @param anchor           锚点
     * @param frequencyHz      关节频率
     * @param dampingRatio     阻尼系数
     * @param angle            两个刚体之间的角度差
     */
    public WeldJointWithTwoBody(String id, World world, boolean collideConnected,
                                RigidBodyShapes bodyA, RigidBodyShapes bodyB, float angle, Vec2 anchor,
                                float frequencyHz, float dampingRatio) {
        //给物理世界类对象赋值
        this.world = world;
        //声明焊接关节描述对象
        WeldJointDef weldJointDef = new WeldJointDef();
        //给关节描述的用户数据赋予关节id
        weldJointDef.userData = id;
        //给是否允许碰撞标志赋值
        weldJointDef.collideConnected = collideConnected;
        //调用焊接关节的初始化方法
        if (bodyA.rigidBody != null && bodyB.rigidBody != null) {
            weldJointDef.initialize(bodyA.rigidBody, bodyB.rigidBody, anchor);
        }
        //给关节频率赋值
        weldJointDef.frequencyHz = frequencyHz;
        //给阻尼系数赋值
        weldJointDef.dampingRatio = dampingRatio;
        //两刚体的角度差
        weldJointDef.referenceAngle = angle;
        //在物理世界添加焊接关节
        this.weldJoint = (WeldJoint) this.world.createJoint(weldJointDef);
    }
}
