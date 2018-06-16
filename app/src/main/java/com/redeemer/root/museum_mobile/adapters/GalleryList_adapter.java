package com.redeemer.root.museum_mobile.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.redeemer.root.museum_mobile.R;
import com.redeemer.root.museum_mobile.model.Gallery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by root on 6/1/18.
 */

public class GalleryList_adapter extends BaseAdapter {

    private Context context;
    private ArrayList<Gallery> galleries;
    private int layout;


    public GalleryList_adapter(Context context, ArrayList<Gallery> paintings, int layout) {
        this.context = context;
        this.galleries = paintings;
        this.layout = layout;
    }

    public void setList(List<Gallery> list){
        galleries.clear();
        galleries.addAll(list);
        sortMyByName();
    }


    private void sortMyByName() {
        //Do sorting of the list

        Collections.sort(galleries, new Comparator<Gallery>() {
            @Override
            public int compare(Gallery p1, Gallery p2) {
                //    return emp1.getFirstName().compareToIgnoreCase(emp2.getFirstName());
                return p1.getArtist().compareToIgnoreCase(p2.getArtist());
            }
        });

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return galleries.size();
    }

    @Override
    public Object getItem(int position) {
        return galleries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list_row = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if(list_row == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            list_row = inflater.inflate(layout, null);
            viewHolder._img = (ImageView) list_row.findViewById(R.id.paint_img);
            viewHolder._artist = (TextView) list_row.findViewById(R.id.artist);
            viewHolder._title = (TextView) list_row.findViewById(R.id.title);
            viewHolder._year = (TextView) list_row.findViewById(R.id.year);
            list_row.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) list_row.getTag();
        }

        Gallery painting = galleries.get(position);

        viewHolder._artist.setText(painting.getArtist());
        viewHolder._title.setText(painting.getTitle());
        viewHolder._year.setText(String.valueOf(painting.getYear()));
        byte[] img_record = painting.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img_record,0,img_record.length);
        viewHolder._img.setImageBitmap(bitmap);
        return list_row;
    }

    private  class  ViewHolder{

        ImageView _img;
        TextView _artist,_title, _year;
    }
}

