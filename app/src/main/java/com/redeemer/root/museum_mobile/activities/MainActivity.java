package com.redeemer.root.museum_mobile.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.redeemer.root.museum_mobile.R;
import com.redeemer.root.museum_mobile.database.Gallery_Database_Helper;
import com.redeemer.root.museum_mobile.model.Gallery;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Gallery_Database_Helper db = new Gallery_Database_Helper(getApplicationContext());
        Gallery gallery;
        //ON CREATE WE ADD 10 PAINTING INTO OUR DATABASE, THIS WHEN EVER THIS ACTIVITY IS CREATED THE PAINTING WILL BE
        //ADD TO THE DATABASE..


        db.insert(gallery =new Gallery(AccessImage(R.drawable.a),"Marting","vintage collect","reflection Of reality","first floor 3",1997));
        db.insert(gallery =new Gallery(AccessImage(R.drawable.b),"King","Taj things","Stories","first floor 6",1991));
        db.insert(gallery =new Gallery(AccessImage(R.drawable.c),"mox","next King","beauty of life","second floor 4",1951));
        db.insert(gallery =new Gallery(AccessImage(R.drawable.d),"Redeemer","Mark","Cangaroo Jack","first floor 9",1987));

        db.insert(gallery =new Gallery(AccessImage(R.drawable.e),"Onks","Things fall Apart","Lost life","first floor 3",1967));
        db.insert(gallery =new Gallery(AccessImage(R.drawable.f),"Shaka","Shaka Zulu","Story of life","first floor 1",1977));
        db.insert(gallery =new Gallery(AccessImage(R.drawable.h),"Shakes","Collection of reality","My painting","first floor 1",1981));
        db.insert(gallery =new Gallery(AccessImage(R.drawable.i),"Charity","Value of Humanity","Evalution of Humans","Ground Floor",1991));

        db.insert(gallery =new Gallery(AccessImage(R.drawable.j),"Kereng","Specil gift","Beauty of life","second floor 3",1951));
        db.insert(gallery =new Gallery(AccessImage(R.drawable.k),"Tefo","Maru a Pula","What goes comes","first floor 5",1967));




    }



    //The method will access an image in a drawable folder by index an vonvert it into abit map
    public byte[] AccessImage(int index){
        Resources res = getResources();
        Drawable drawable = res.getDrawable(index);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        return bitMapData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.open_app,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.open:
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
