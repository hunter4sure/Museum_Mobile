package com.redeemer.root.museum_mobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.redeemer.root.museum_mobile.R;

public class MasterView extends AppCompatActivity {

    TextView _artist,_title,_desc,_room,_year,_rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_view);

        _artist =(TextView) findViewById(R.id.artist);
        _title =(TextView) findViewById(R.id.title);
        _desc =(TextView) findViewById(R.id.desc);
        _room =(TextView) findViewById(R.id.room);
        _year =(TextView) findViewById(R.id.year);
        _rank =(TextView) findViewById(R.id.rank);

        String artist = getIntent().getStringExtra("artist");
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        String room = getIntent().getStringExtra("room");
        int  year = getIntent().getIntExtra("year", 0);
        int rank = getIntent().getIntExtra("rank", 0);
        
        _artist.setText(artist);
        _title.setText(title);
        _desc.setText(desc);
        _room.setText(room);
        _year.setText(String.valueOf(year));
        _rank.setText(String.valueOf(rank));
    }

    Intent  intent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.adpainting:
              Intent  intent = new Intent(getApplicationContext(), NewPainting.class);
                startActivity(intent);
                return  true;

            case R.id.MasterView:
                  intent = new Intent(getApplicationContext(), GalleryCollection.class);
                startActivity(intent);
                return  true;

            case R.id.prices:
                  intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                return  true;
            case R.id.location:
                intent = new Intent(getApplicationContext(), MuseumLocation.class);
                startActivity(intent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
