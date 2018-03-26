package com.example.imtiazaminsajid.musicplayer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView playListLV;
    String[] items;
    ArrayList<File> mySongs;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playListLV = findViewById(R.id.playList);

        mySongs = songs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];

        for(int i = 0; i<mySongs.size(); i++){
            items[i]= mySongs.get(i).getName().toString().replace(".mp3","");
        }

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        playListLV.setAdapter(arrayAdapter);
        playListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
    }

    public ArrayList<File> songs(File root){
        ArrayList<File> arrayList = new ArrayList<File>();

        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
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
