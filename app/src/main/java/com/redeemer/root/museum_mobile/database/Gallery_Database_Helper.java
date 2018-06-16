package com.redeemer.root.museum_mobile.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.redeemer.root.museum_mobile.model.Gallery;

/**
 * Created by root on 6/1/18.
 */

public class Gallery_Database_Helper extends SQLiteOpenHelper {


    //database name
    private static final String dbName = "PAINTINGS.sqlite";

    //database tabale name
    public static  final String tbName= "PAINTING";
    private static final int version =1;

    private static final String IDColumn = "ID";
    private static final String imgColumn = "Image";
    private static final String ARTISTNAMEColumn = "artist";
    private static final String titleColumn = "title";
    private static final String descColumn = "description";
    private static final String roomColumn = "room";
    private static final String yearColumn = "year";
    private static final String rankColumn = "rank";
    private static final String ColumnId = "id";

    //Dataabse column names


    public Gallery_Database_Helper(Context context) {
        super(context, dbName, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tbName + "(" +
                 IDColumn + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                imgColumn + " BLOB," +
                ARTISTNAMEColumn + " text," +
                titleColumn+ " text," +
                descColumn+ " text," +
                roomColumn + " text," +
                yearColumn+ " INTEGER," +
                rankColumn + " INTEGER  DEFAULT 0" +")");
    }

    public void queryData(String query){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(query);
    }


    public void insert(Gallery galley){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to insert record into  database.

        String query = "INSERT INTO " + tbName + "(" + imgColumn +"," +ARTISTNAMEColumn + "," +titleColumn + "," + descColumn + ","+ roomColumn + "," + yearColumn + ") VALUES(?, ?,?,?,?,?) ";

        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(query);

        sqLiteStatement.clearBindings();

        sqLiteStatement.bindBlob(1,galley.getImg());
        sqLiteStatement.bindString(2,galley.getArtist());
        sqLiteStatement.bindString(3,galley.getTitle());
        sqLiteStatement.bindString(4,galley.getDesc());
        sqLiteStatement.bindString(5,galley.getRoom());
        sqLiteStatement.bindLong(6,galley.getYear());

        sqLiteStatement.executeInsert();

        System.out.println("Inserting into the Database");

    }



    public void update(Gallery gallery){


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to insert record into  database.

        String query = "UPDATE " + tbName + " SET " + imgColumn + " =?, " + ARTISTNAMEColumn + " = ?, " + titleColumn + " = ?, " + descColumn + "=?, " + roomColumn + " =?,  " + yearColumn + "=?, WHERE  " + ColumnId + "=? ";

        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(query);

        sqLiteStatement.clearBindings();


        sqLiteStatement.bindBlob(1,gallery.getImg());
        sqLiteStatement.bindString(2,gallery.getArtist());
        sqLiteStatement.bindString(3,gallery.getTitle());
        sqLiteStatement.bindString(4,gallery.getDesc());
        sqLiteStatement.bindString(5,gallery.getRoom());
        sqLiteStatement.bindLong(6,gallery.getYear());
        sqLiteStatement.bindLong(7,gallery.getId());

        sqLiteStatement.execute();

        sqLiteStatement.close();

    }

    public void delete(Gallery gallery){


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to insert record into  database.

        String query = "DELETE FROM " + tbName + " WHERE " +ColumnId + " = ?";

        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(query);

        sqLiteStatement.clearBindings();
        sqLiteStatement.bindLong(1,gallery.getId());

        sqLiteStatement.execute();

        sqLiteStatement.close();
    }



    public Cursor getGallery(String query){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return  sqLiteDatabase.rawQuery(query,null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
