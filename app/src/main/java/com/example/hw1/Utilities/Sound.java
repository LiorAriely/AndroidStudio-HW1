package com.example.hw1.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class Sound {

    private Context context;
    private Executor executor;
    //private MediaPlayer mediaPlayer;

    public Sound(Context context) {
        this.context = context.getApplicationContext();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playSound(int res) {
        executor.execute(() -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, res);
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.stop();
                mp.release();
                mp = null;
            });
        });
    }

}