package com.example.myapplication;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {
    private Handler mHandle=new Handler();
    private Runnable runnable;
     SeekBar sb;
     TextView t1,t2,title;
    MediaPlayer music;
    Button b1;
    Boolean isOn=false;
    int resID; String sname;
    int [] song={R.raw.perfect,R.raw.alright,R.raw.moon,R.raw.passenger,R.raw.pink,};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sname=getIntent().getExtras().getString("sname").toLowerCase(Locale.ROOT);
        resID=getResources().getIdentifier(sname,"raw",getPackageName());
        music = MediaPlayer.create(this,resID);
        b1=findViewById(R.id.play);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        title=findViewById(R.id.title);
        title.setText(sname.toUpperCase());
        //Setting song duration
        t2.setText(convertTOMSS(String.valueOf(music.getDuration())));
        sb=findViewById(R.id.sb);
        sb.setMax(music.getDuration());
        sb.setProgress(music.getCurrentPosition());
        //To update seekbar progress with song
        MainActivity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(music!=null){
                    sb.setProgress(music.getCurrentPosition());
                    t1.setText(convertTOMSS(String.valueOf(music.getCurrentPosition())));
                    new Handler().postDelayed(this,100);
                }
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(music!=null&&fromUser){
                    music.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

       }
       private void changeSeekbar(){
        sb.setProgress(music.getCurrentPosition());
        if(music.isPlaying()){
            runnable=new Runnable() {
                @Override
                public void run() {
                        changeSeekbar();
                    }
            };
            mHandle.postDelayed(runnable,1000);
        }
       }
    @SuppressLint("DefaultLocale")
    //Program to convert milliseconds into minute and seconds
    public static String convertTOMSS(String duration) {
        Long ms=Long.parseLong(duration);
        return String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(ms)%TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(ms)% TimeUnit.MINUTES.toSeconds(1));
    }

    //For playing song
       public void play(View v){
           if(!isOn){
               music.start();
               if(music.isPlaying()){
                   b1.setBackgroundResource(R.drawable.pause);
               }
               isOn=true;
           }
           else{
               music.pause();
               b1.setBackgroundResource(R.drawable.play);
               isOn=false;}
       }
       //To play previous song
        public void previous(View v){
        music.stop();
        music = MediaPlayer.create(this, R.raw.perfect);
        music.start();

    }
    //To play next song
    public void next(View v)
    {
       music.stop();
       music = MediaPlayer.create(this, R.raw.moon);
       music.start();
    }
    public void onDestroy(){
        music.release();
        music=null;
        super.onDestroy();
    }
}