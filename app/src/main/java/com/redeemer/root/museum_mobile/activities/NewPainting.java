package com.redeemer.root.museum_mobile.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.redeemer.root.museum_mobile.R;
import com.redeemer.root.museum_mobile.database.Gallery_Database_Helper;
import com.redeemer.root.museum_mobile.model.Gallery;

import java.io.ByteArrayOutputStream;

public class NewPainting extends AppCompatActivity {

    private EditText painter_name,paint_title, room, pain_desc,year;
    Button save_btn;
    ImageView painting_img;
    private static final int REQUEST_CODE_GALLERY = 999;

    Gallery_Database_Helper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_painting);

        painter_name = (EditText) findViewById(R.id.artist);
        paint_title = (EditText) findViewById(R.id.title);
        room = (EditText) findViewById(R.id.room);
        pain_desc =(EditText)  findViewById(R.id.desc);
        year = (EditText) findViewById(R.id.year);
        painting_img = (ImageView) findViewById(R.id.imgView);

        save_btn =(Button) findViewById(R.id.save);

        db_helper =new Gallery_Database_Helper(getApplicationContext());


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gallery painting =new Gallery();
                painting.setArtist(painter_name.getText().toString().trim());
                painting.setTitle(paint_title.getText().toString().trim());
                painting.setDesc(pain_desc.getText().toString().trim());
                painting.setYear(Integer.parseInt(year.getText().toString().trim()));
                painting.setRoom(room.getText().toString().trim());
                painting.setImg(ImageViewToBlob(painting_img));

                //SAVE PAINTING TO DATABASE..

                try{

                    db_helper.insert(painting);

                    Toast.makeText(getApplicationContext(), "Record Inserted Successfully",Toast.LENGTH_LONG).show();
                    painter_name.setText("");
                    paint_title.setText("");
                    pain_desc.setText("");
                    year.setText("");
                    room.setText("");
                    painting_img.setImageResource(R.mipmap.ic_launcher);
                } catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        painting_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(
                        NewPainting.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }


    private byte[] ImageViewToBlob(ImageView image) {
        Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] bytearray = stream.toByteArray();

        return  bytearray;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){

            Uri imguri = data.getData();

            painting_img.setImageURI(imguri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Don't have Permission to access file location", Toast.LENGTH_LONG).show();
            }

            return;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            case R.id.location:
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
        }
        return super.onOptionsItemSelected(item);
    }


}
