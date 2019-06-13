package com.example.liaoyikang.tomandjerry;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Animation mhelpani ;
    ImageView mhelp ;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mhelp = findViewById(R.id.help);
        mhelpani = AnimationUtils.loadAnimation(this,R.anim.help);
        mhelp.setAnimation(mhelpani);
        mhelpani.start();
        mp = MediaPlayer.create(this,R.raw.bgm);
    }

    public void choose(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    public void music(View view) {
        if(mp.isPlaying()){
            mp.stop();
            try {
                mp.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mp.seekTo(0);
        }
        else {
            mp.start();
        }

    }
}
