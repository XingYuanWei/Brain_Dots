package com.example.astronaut.brain_dots.View.componentShow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.Window;
import android.widget.CompoundButton;

import com.example.astronaut.brain_dots.R;
import com.example.astronaut.brain_dots.Utils.gameUtils.BackgroundMusicUtil;


/*
 *Created by 魏兴源 on 2018-08-26
 *Time at 16:35
 *
 */


/**
 * 自定义一个弹出对话框
 */
public class SettingDialog extends Dialog {
    public static boolean soundFlag = true;
    public static boolean soundEffectFlag = true;

    public SettingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //对话框的布局
        setContentView(R.layout.setting_layout_dialog);
        //获取声音开关控件
        SwitchCompat switchSound = findViewById(R.id.switchSound);
        /*
          初始化时候设置它的Checked为soundFlag
          并且在下一次打开它的时候能够记录下此时
          状态,比如背景音乐选择了Off,下一次打开对话框的时候
          还是要off(开关为关闭状态),而不是又被初始化为打开状态
          由于此对话框是根据Activity的存在而存在.所以每次构造新的
          对话框时候都会把soundFlag标记重新构造,所以把这两个标记定义为类变量. ;
            如果在不是一直构造新的对话框对象,则可以不设置为类变量.
          */
        switchSound.setChecked(soundFlag);
        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //设置了setChecked为true时候 但是此时的isChecked还是为false
                if (!isChecked) {
                    buttonView.setChecked(false);
                    BackgroundMusicUtil.playBackGroundMusic(false);
                    soundFlag = false;
                } else {
                    buttonView.setChecked(true);
                    BackgroundMusicUtil.playBackGroundMusic(true);
                    soundFlag = true;
                }
            }
        });
        //音效开关
        SwitchCompat switchSoundEffect = findViewById(R.id.switchSoundEffect);
        switchSoundEffect.setChecked(soundEffectFlag);
        switchSoundEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    buttonView.setChecked(false);
                    soundEffectFlag = false;
                } else {
                    buttonView.setChecked(true);
                    soundEffectFlag = true;
                }
            }
        });
    }
}
