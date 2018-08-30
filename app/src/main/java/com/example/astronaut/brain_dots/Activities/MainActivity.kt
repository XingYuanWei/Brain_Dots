package com.example.astronaut.brain_dots.Activities

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.example.astronaut.brain_dots.Domain.Creator
import com.example.astronaut.brain_dots.Shapes.rules.RigidBodyShapes
import com.example.astronaut.brain_dots.Utils.ColorUtil
import com.example.astronaut.brain_dots.Utils.Constant
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import java.util.*

class MainActivity : AppCompatActivity() {
    private val startActivity = "START_GAME"
    //创建一个世界
    var world: World? = null
    //存放绘制物体的集合
    var shapesList = ArrayList<RigidBodyShapes>()//物体列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        //获取尺寸
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        if (dm.widthPixels < dm.heightPixels) {
            Constant.SCREEN_WIDTH = dm.widthPixels
            Constant.SCREEN_HEIGHT = dm.heightPixels
        } else {
            Constant.SCREEN_WIDTH = dm.heightPixels
            Constant.SCREEN_HEIGHT = dm.widthPixels
        }
        //屏幕自适应
        Constant.ScaleSR()

        Log.e("Tag!!", "Width:" + Constant.SCREEN_WIDTH + "Height:" + Constant.SCREEN_HEIGHT)
        /**
         * 创建一 个物理世界对象，必须要给出其重力向量（若没有重力可以给 0 值）。
         *
         * */
        //重力
        val gravity = Vec2(0f, 10.0f)
        //创建世界
        world = World(gravity)

        //创建一个蓝球
//        val blueBall = Creator.createBall(600f, 200f, 50f, false, world, ColorUtil.getBlue())
//        shapesList.add(blueBall)
//        //创建红球
//        val redBall = Creator.createBall(400f, 200f, 50f, false, world, ColorUtil.getRed())
//        shapesList.add(redBall)
        /**
         * SetLinearVelocity是直接用来改变物体的速度，
         * 中间没有什么加速度的概念，直接定义新速度。
         * 新速度和原速度没有关系，这就是和ApplyImpulse的区分。
         *
         * */
//        blueBall.rigidBody.linearVelocity = Vec2(0f, 60f)

        //底板
        val rectangle = Creator.createRectangle(300f, (Constant.SCREEN_WIDTH) * 0.1f, 300f, 20f, true,
                world, ColorUtil.getGray())
        shapesList.add(rectangle)

//        val rectangle1 = Creator.createRectangle(500f, 400f, 30f,
//                5f, false, world, ColorUtil.getGray(), 0f)
//        val rectangle2 = Creator.createRectangle(580f, 400f, 30f,
//                5f, false, world, ColorUtil.getGray(), 10f)
//        val rectangle3 = Creator.createRectangle(610f, 400f, 30f,
//                5f, false, world, ColorUtil.getGray(), 0f)

        Log.d("tag!!", "" + Math.toDegrees(0.5233))

//        WeldJointWithTwoBody("1", world, false, rectangle1, rectangle2, 0f,
//                Vec2(550f, 400f), 15f, 0f)
//        WeldJointWithTwoBody("1", world, false, rectangle2, rectangle3, 0f,
//                Vec2(590f, 400f), 15f, 0f)
//        shapesList.add(rectangle1)
//        shapesList.add(rectangle2)
//        shapesList.add(rectangle3)


        val fileName = "Level/level2.map"
//        val list = ReadMapData.getMapElement(this, fileName)

        //创建游戏面板
//        val gameView = ShowGameSurfaceView(this)
//        setContentView(gameView)
    }
}
