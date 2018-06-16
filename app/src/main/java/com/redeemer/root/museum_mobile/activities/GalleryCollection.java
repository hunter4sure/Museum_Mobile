package com.redeemer.root.museum_mobile.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;



import com.redeemer.root.museum_mobile.R;
import com.redeemer.root.museum_mobile.fragments.AllFragment;
import com.redeemer.root.museum_mobile.fragments.RankedFragment;
import com.redeemer.root.museum_mobile.fragments.UnRankedFragment;

public class GalleryCollection extends AppCompatActivity {


    BottomNavigationView bottomNavView;
    FrameLayout frameLayout;

    AllFragment allPainting;
    RankedFragment rankedrankerPainting ;
    UnRankedFragment unrankedPainting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_collection);


        frameLayout =(FrameLayout) findViewById(R.id.mainframe);
        bottomNavView =(BottomNavigationView) findViewById(R.id.bottomNav);


        allPainting = new AllFragment();
        rankedrankerPainting = new RankedFragment();
        unrankedPainting = new UnRankedFragment();

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.all:
                        CallFragment(allPainting);
                        return  true;
                    case R.id.ranked:
                        CallFragment(rankedrankerPainting);
                        return  true;
                    case R.id.unranked:
                        CallFragment(unrankedPainting);
                        return  true;
                    default:
                        return  false;


                }
            }
        });

    }


    //select fragmentent depending on the user selection
    public void CallFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainframe,fragment);
        transaction.commit();

    }


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
                Intent intent = new Intent(getApplicationContext(), NewPainting.class);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
