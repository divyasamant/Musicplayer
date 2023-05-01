package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    EditText et;
    TextView title_song,fav;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.lv);
        fav=findViewById(R.id.Fav);
        title_song=findViewById(R.id.song);
        title_song.setTextColor(Color.RED);
        String[] song={"Perfect","Alright","Moon","Passenger","Pink"};
        ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,song);
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
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i=new Intent(getApplicationContext(),Favourite.class);
             startActivity(i);
            }
        });
    }
}
