package com.example.astronaut.brain_dots.Utils.gameUtils;


import android.util.Log;

import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.View.componentShow.SettingDialog;
import com.example.astronaut.brain_dots.View.gameShow.ShowGameSurfaceView;

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

    public GameContactListener(ShowGameSurfaceView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void beginContact(Contact contact) {
        //用物理引擎里的方法判断是否为两个小球碰撞
        boolean isBallContact = contact instanceof CircleContact;
        Log.e("Tag!!", "beginContact: " + contact );
        if (isBallContact) {
            Log.e("Tag!!", "beginContact: " + "碰到啦！！！");
            //游戏属于标志置为1
            Constant.STATE = 1;
            //把动画线程的标志设为false,画面停止
            Constant.DRAW_THREAD_FLAG = false;

            /*
             * 如果开启了音效,那么播放碰撞成功的音乐
             * @loop:循环播放的次数
             * 0为值播放一次
             * -1为无限循环
             * 其他值为播放loop+1次(例如，3为一共播放4次)
             *
             * */
            //判断是否开启了音效
            if (SettingDialog.soundEffectFlag) {
                BackgroundMusicUtil.playSound(1, 1);
                Log.e("Tag!!", "beginContact: " + "1111111111");
            }
            //画面停止2.5s之后,发送消息到Handler切换页面
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //如果背景音乐是开着的话,那么暂停2.5s后继续背景音乐
            if (SettingDialog.soundFlag) {
                BackgroundMusicUtil.playBackGroundMusic(true);
            }
            //发送消息把界面切换到胜利的界面
            gameView.activity.mainHandler.sendEmptyMessage(Constant.HANDLER_MSG_PASS);
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
