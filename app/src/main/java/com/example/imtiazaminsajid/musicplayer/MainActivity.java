package com.example.imtiazaminsajid.musicplayer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView playListLV;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playListLV = findViewById(R.id.playList);

        ArrayList<File> mySong = songs(Environment.getExternalStorageDirectory());
        items = new String[mySong.size()];

        for(int i = 0; i<mySong.size(); i++){
            items[i]= mySong.get(i).getName().toString().replace(".mp3","");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
        playListLV.setAdapter(arrayAdapter);
    }

    public ArrayList<File> songs(File root){
        ArrayList<File> arrayList = new ArrayList<File>();

        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && singleFile.isHidden()){
                arrayList.addAll(songs(singleFile));

            }
            else {
                if(singleFile.getName().endsWith(".mp3")){
                    arrayList.add(singleFile);

                }
            }
        }
        return arrayList;

    }
}
