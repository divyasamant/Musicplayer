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
    SeekBar sb;
    TextView t1,t2,title;
    MediaPlayer music;
    Button b1,b2;
    Boolean isOn=false;
    int resID; String sname;
    int[] song;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sname=getIntent().getExtras().getString("sname").toLowerCase(Locale.ROOT);
        resID=getResources().getIdentifier(sname,"raw",getPackageName());
        music = MediaPlayer.create(this,resID);
        b1=findViewById(R.id.play);
        b2=findViewById(R.id.next);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        title=findViewById(R.id.title);
        title.setText(sname.toUpperCase());
        //resource ids
        int a=getResources().getIdentifier("perfect","raw",getPackageName());
        int b=getResources().getIdentifier("alright","raw",getPackageName());
        int c=getResources().getIdentifier("moon","raw",getPackageName());
        int d=getResources().getIdentifier("passenger","raw",getPackageName());
        int e=getResources().getIdentifier("pink","raw",getPackageName());
        song= new int[]{a, b, c, d, e};
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
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(v);
            }
        });

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
            music.reset();
            music.release();
            music=null;
            for(int i=0;i<song.length;i++){
                if(resID==song[i]){
                    int new_song=song[i-1];
                    String new_song_name= getResources().getResourceEntryName(song[i-1]);
                    music = MediaPlayer.create(this,new_song);
                    music.start();b1.setBackgroundResource(R.drawable.pause);
                    title.setText(new_song_name.toUpperCase());
                    resID=song[i-1];
                }
            }
    }
    public void next(View v){
        music.stop();
        music.reset();
        music.release();
        music=null;
        for(int i=0;i<song.length;i++){
            if(resID==song[i]){
                int new_song=song[i+1];
                String new_song_name= getResources().getResourceEntryName(song[i+1]);
                music = MediaPlayer.create(this,new_song);
                music.start();b1.setBackgroundResource(R.drawable.pause);
                title.setText(new_song_name.toUpperCase());
                resID=song[i+1];
            }
        }
    }
    public void onDestroy(){
        super.onDestroy();
        music.stop();
        music.reset();
        music.release();
        music=null;

    }
}