package com.example.astronaut.brain_dots.Utils;

import com.example.astronaut.brain_dots.Utils.screenManagerUtils.ScreenScaleResult;
import com.example.astronaut.brain_dots.Utils.screenManagerUtils.ScreenScaleUtil;

import java.io.Serializable;


/**
 * 该类定义的常量用于各种计算
 */
public class Constant {
    //表示在onActivityResult()中更新金钱的数量的返回值(在)
    public final static int REQUEST_REFRESH_GOLD_NUM_CODE = 1001;
    //花费金币后 更新金钱数量的Handler标记
    public static final int SUB_GOLD_NUM_HANDLER = 10;
    //增加金币后 更新金钱数量的Handler标记
    public static final int ADD_GOLD_NUM_HANDLER = 11;
    /*
     * 该常量用于传递给Handler的消息,而内置的what变量可以为整型常量
     * 要用final来修饰该变量
     * 0 表示未通关
     * 1 表示通关
     *
     * */

    /*
     * 表示游戏金币的数量
     *
     * */
    public static int MONEY = 100;
    public static final int HANDLER_MSG_LOSE = 0;
    public static final int HANDLER_MSG_PASS = 1;
    /*
     * 该状态表示游戏通过或失败,用于画星星或者失败的表情,-1为初始状态
     * 0表示游戏失败,1表示通过
     * */

    public static int STATE = -1;
    //屏幕到现实世界的比例 10px：1m;

    public static final float RATE = 10;
    /*
     *  绘制线程工作标志位
     *  当红蓝两个小球相撞的时候,把此flag置为false,
     *  线程停止,画面停止
     *
     * */
    public static final int STOP_LOADING = 3;
    /*
     * 告诉Activity进入加载界面的Handler标志
     * */
    public static final int NEXT_LEVEL = 4;
    //重新开始这关卡游戏的Handler标志
    public static final int RENEW_GAME = 5;
    public static boolean IS_NEW_THREAD = true;
    //是否开启世界模拟的标志
    public static boolean DRAW_THREAD_FLAG = true;
    public static final float TIME_STEP = 2.0f / 60.0f;//模拟的的频率
    public static final int ITERATIONS = 10;//迭代越大，模拟越精确，但性能越低
    public static int SCREEN_WIDTH = 720;//目标屏幕宽度
    public static int SCREEN_HEIGHT = 1280;//目标屏幕高度
    public static float x;//声明当前屏幕左上方X值的变量
    public static float y;//声明当前屏幕左上方Y值的变量

    private static float ratio;//声明缩放比例的变量

    //单位矩形的长和宽
    public static final int RECTANGLE_WIDTH = 100;
    public static final int RECTANGLE_HEIGHT = 10;
    //单位线段长度的值
    public static final int segmentLength = 100;

    public static void ScaleSR() {
        ScreenScaleResult screenScaleResult = ScreenScaleUtil.calScale(SCREEN_WIDTH, SCREEN_HEIGHT);
        x = screenScaleResult.lucX;//获取当前屏幕左上方的X值
        y = screenScaleResult.lucY;//获取当前屏幕左上方的Y值
        ratio = screenScaleResult.ratio;//获取缩放比例
    }
}