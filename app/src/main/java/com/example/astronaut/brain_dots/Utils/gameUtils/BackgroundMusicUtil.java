package com.example.astronaut.brain_dots.Utils.gameUtils;

/*
 *Created by 魏兴源 on 2018-07-14
 *Time at 10:14
 *
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.astronaut.brain_dots.R;

import java.util.HashMap;

public class BackgroundMusicUtil {
    //背景音乐
    private static MediaPlayer backgroundMusic;
    //声音池
    private static SoundPool soundPool;
    //存放声音ID的集合
    private static HashMap<Integer, Integer> soundPoolMap;

    //初始化音乐
    @SuppressLint("UseSparseArrays")
    public static void initSound(Activity activity) {
        if (backgroundMusic != null) {
            return;
        }
        //背景音乐
        backgroundMusic = MediaPlayer.create(activity, R.raw.backmusic);
        backgroundMusic.setLooping(true);

        //声音池
        SoundPool.Builder soundBuilder = new SoundPool.Builder();
        //设置同时的最大数量
        soundBuilder.setMaxStreams(4);
        //准备声音池属性
        AudioAttributes.Builder audioBuilder = new AudioAttributes.Builder();
        //音频类型
        audioBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
        //声音池属性
        soundBuilder.setAudioAttributes(audioBuilder.build());
        //创建声音池
        soundPool = soundBuilder.build();
        //定义一个HashMap用于存放音频流的ID
        soundPoolMap = new HashMap<>();

        //碰撞音乐
        soundPoolMap.put(1, soundPool.load(activity, R.raw.contact, 1));
        //出现音乐
        soundPoolMap.put(2, soundPool.load(activity, R.raw.comeout, 1));
        //失败音乐
        soundPoolMap.put(3, soundPool.load(activity, R.raw.failed, 1));
    }

    //播放音乐
    public static void playSound(int soundName, int loop) {
        /**
         *
         * 下面注释的代码根据当前媒体音量和媒体音量的最大值，
         * 计算出一个比值传递给play方法，作为左右声道的音量，
         * 这样的结果是缩小了实际音量！尤其是（Bug），
         * 你在播放开始前将媒体音量调整为0，开始播放后，你将听不到声音！
         * 此时你再通过音量键调整音量，也听不到声音！（我今天刚解了一个类似的Bug）
         * 所以，如果想按照用户设置的音量来播放音效，
         * 正确的做法是：mSoundPool.play(soundID, 1, 1, 1, -1, 1);
         *
         * */
//        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
//        float streamVolumeCurrent = Objects.requireNonNull(audioManager).getStreamVolume(AudioManager.STREAM_MUSIC);
//        float streamVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        float volume = streamVolumeCurrent / streamVolumeMax;
        soundPool.play(soundPoolMap.get(soundName), 1, 1, 1, loop, 1f);
    }

    //播放背景音乐
    public static void playBackGroundMusic(boolean state) {
        if (backgroundMusic != null) {
            if (state) {
                backgroundMusic.start();
            } else {
                backgroundMusic.pause();
            }
        }
    }
}
