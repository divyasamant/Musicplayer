package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Favourite extends AppCompatActivity {
    ListView lv;
    TextView fav,song;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoutites);
        String[] fav_song={"Perfect","Alright"};
        lv=findViewById(R.id.lv);
        song=findViewById(R.id.song);
        fav=findViewById(R.id.Fav);
        fav.setTextColor(Color.RED);
        ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,fav_song);
        lv.setAdapter(a);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sname=lv.getAdapter().getItem(position).toString();
                Intent i=new Intent(getApplicationContext(),MainActivity2.class);
                i.putExtra("sname",sname);
                startActivity(i);
            }
        });
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}