package com.example.imtiazaminsajid.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener {
    static MediaPlayer mediaPlayer;
    ArrayList<File> mySongs;
    int position;
    Uri uri;
    Thread updateSeekBar;

    SeekBar seekBar;
    Button pause,next,previous,fastForword,backForword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        seekBar = findViewById(R.id.seekBar);

        pause = findViewById(R.id.playBT);
        next = findViewById(R.id.nextBT);
        previous = findViewById(R.id.forwordBT);
        fastForword = findViewById(R.id.fastForwordBT);
        backForword = findViewById(R.id.fastBackwordBT);

        pause.setText("||");
        next.setText(">|");
        previous.setText("|<");
        fastForword.setText(">>");
        backForword.setText("<<");

        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        fastForword.setOnClickListener(this);
        backForword.setOnClickListener(this);

        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                //super.run();
            }
        };

        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("mySongs");
        position = bundle.getInt("position", 0);

        uri = Uri.parse(mySongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext() , uri);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.playBT:
                if(mediaPlayer.isPlaying()){
                    pause.setText(">");
                    mediaPlayer.pause();
                }
                else {
                    pause.setText("||");
                    mediaPlayer.start();
                }

                break;

            case R.id.fastForwordBT:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
                break;

            case R.id.fastBackwordBT:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
                break;

            case R.id.nextBT:
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position+1)%mySongs.size();
                uri = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext() , uri);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                break;

            case R.id.forwordBT:
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position-1<0)?mySongs.size()-1: position-1;
                uri = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext() , uri);
                mediaPlayer.start();
                break;

        }
    }
}
