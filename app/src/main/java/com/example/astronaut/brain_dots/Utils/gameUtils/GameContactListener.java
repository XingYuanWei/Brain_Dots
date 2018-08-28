package com.example.astronaut.brain_dots.Utils.gameUtils;


import android.util.Log;

import com.example.astronaut.brain_dots.Activities.GameViewActivity;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.View.show.PassTwinkle;
import com.example.astronaut.brain_dots.View.show.SettingDialog;
import com.example.astronaut.brain_dots.View.show.ShowGameSurfaceView;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.CircleContact;
import org.jbox2d.dynamics.contacts.Contact;


/*
 *Created by 魏兴源 on 2018-08-26
 *Time at 23:08
 *
 */

public class GameContactListener implements ContactListener {
    private ShowGameSurfaceView gameView;
    //画出三个星星 其中两两x坐标相差300,y坐标相差200
    private float offsetX = 320;
    private float offsetY = 180;

    public GameContactListener(ShowGameSurfaceView gameView) {
        this.gameView = gameView;
    }

    public GameContactListener() {
    }

    @Override
    public void beginContact(Contact contact) {
        //用物理引擎里的方法判断是否为两个小球碰撞
        boolean isBallContact = contact instanceof CircleContact;
        if (isBallContact) {
            Log.e("Tag!!", "beginContact: "+"碰到啦！！！" );
            //在集合中添加三个星星
            {
                //先清空原有的,不然可能会重叠,可能看不出来会重叠,但是集合中确实有多个PassTwinkle对象
                gameView.twinkleList.clear();
                float halfHeight = Constant.RECTANGLE_HEIGHT / 2 * 150;
                //六分之一的宽
                float one_sixWidth = Constant.RECTANGLE_WIDTH / 6;
                gameView.twinkleList.add(new PassTwinkle(halfHeight, one_sixWidth));
                gameView.twinkleList.add(new PassTwinkle(halfHeight - offsetX, one_sixWidth + offsetY));
                gameView.twinkleList.add(new PassTwinkle(halfHeight + offsetX, one_sixWidth + offsetY));
            }
            //把动画线程的标志设为false,画面停止
            Constant.DRAW_THREAD_FLAG = false;
            //背景音乐也暂停3s
            BackgroundMusicUtil.playBackGroundMusic(false);
            /*
             * 如果开启了音效,那么播放碰撞成功的音乐
             * @loop:循环播放的次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次(例如，3为一共播放4次)
             *
             * */
            //判断是否开启了音效
            if (SettingDialog.soundEffectFlag) {
                BackgroundMusicUtil.playSound(1, 1);
                Log.e("Tag!!", "beginContact: "+"1111111111" );
            }
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //如果背景音乐是开着的话,那么暂停3s后继续背景音乐
            if (SettingDialog.soundFlag) {
                BackgroundMusicUtil.playBackGroundMusic(true);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
