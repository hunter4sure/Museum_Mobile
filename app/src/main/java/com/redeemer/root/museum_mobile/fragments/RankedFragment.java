package com.redeemer.root.museum_mobile.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.redeemer.root.museum_mobile.R;
import com.redeemer.root.museum_mobile.activities.MasterView;
import com.redeemer.root.museum_mobile.adapters.GalleryList_adapter;
import com.redeemer.root.museum_mobile.database.Gallery_Database_Helper;
import com.redeemer.root.museum_mobile.model.Gallery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankedFragment extends Fragment {


    SearchView searchView;
    ListView listView;
    ArrayList<Gallery> galleries;
    GalleryList_adapter adapter = null;
    ImageView icon;
    static Gallery_Database_Helper db_helper;
    private static final int REQUEST_CODE_GALLERY = 888;


    public RankedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);


        listView = (ListView) view.findViewById(R.id.listview);
        galleries = new ArrayList<>();

        adapter = new GalleryList_adapter(getActivity().getApplicationContext(), galleries, R.layout.list_row);


        listView.setAdapter(adapter);

        db_helper = new Gallery_Database_Helper(getActivity().getApplicationContext());


        Cursor cursor = db_helper.getGallery(
                "SELECT * FROM " + Gallery_Database_Helper.tbName + " where rank > 0");

        galleries.clear();


        while (cursor.moveToNext()) {


            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String artist = cursor.getString(2);
            String title = cursor.getString(3);
            String desc = cursor.getString(4);
            String room = cursor.getString(5);
            int year = cursor.getInt(6);
            int rank = cursor.getInt(7);

            Gallery painting = new Gallery();

            painting.setImg(img);
            painting.setId(id);
            painting.setRoom(room);
            painting.setDesc(desc);
            painting.setRank(rank);
            painting.setArtist(artist);
            painting.setTitle(title);
            painting.setYear(year);

            galleries.add(painting);

        }
        adapter.notifyDataSetChanged();

        if (galleries.size() == 0) {

            Toast.makeText(getActivity().getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                CharSequence[] items = {"Update", "Delete", "Detailed View", "Rank Picture"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Choose Action");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        if (i == 0) {

                            Cursor c = db_helper.getGallery("SELECT ID FROM PAINTING where rank > 0");
                            ArrayList<Integer> ids = new ArrayList<>();

                            while (c.moveToNext()) {

                                ids.add(c.getInt(0));

                            }//show Update dialog

                            ShowDialog(getActivity(), ids.get(position));

                        }
                        if (i == 1) {

                            Cursor c = db_helper.getGallery("SELECT id FROM PAINTING");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));

                        }
                        if (i == 2) {

                            Cursor c = db_helper.getGallery("SELECT id FROM PAINTING");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            OpenPictureProfile(arrID.get(position));
                        }
                        if (i == 3) {


                            Cursor c = db_helper.getGallery("SELECT id FROM PAINTING");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            RateApp(getActivity(), arrID.get(position));

                        }


                    }
                });

                builder.show();

            }
        });


        return view;
    }

    private void OpenPictureProfile(int integer) {

        //get all data from sqlite
        Cursor cursor = db_helper.getGallery("SELECT * FROM " + Gallery_Database_Helper.tbName + "  WHERE id = " + integer);
        galleries.clear();

        while (cursor.moveToNext()) {

            byte[] img = cursor.getBlob(1);
            String artist = cursor.getString(2);
            String title = cursor.getString(3);
            String desc = cursor.getString(4);
            String room = cursor.getString(5);
            int year = cursor.getInt(6);
            int rank = cursor.getInt(7);

            Intent intent = new Intent(getContext(), MasterView.class);
            intent.putExtra("artist", artist);
            intent.putExtra("title", title);
            intent.putExtra("desc", desc);
            intent.putExtra("room", room);
            intent.putExtra("year", year);
            intent.putExtra("rank", rank);
            startActivity(intent);
        }


    }

    private void showDialogDelete(final Integer idRecord) {

        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Gallery p = new Gallery();
                    p.setId(idRecord);
                    db_helper.delete(p);
                    Toast.makeText(getActivity(), "Delete successfully", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateRecordList() {
        //get all data from sqlite
        Cursor cursor = db_helper.getGallery("SELECT * FROM PAINTING where rank >0");
        galleries.clear();
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String artist = cursor.getString(2);
            String title = cursor.getString(3);
            String desc = cursor.getString(4);
            String room = cursor.getString(5);
            int year = cursor.getInt(6);
            int rank = cursor.getInt(7);

            Gallery painting = new Gallery();

            painting.setImg(img);
            painting.setId(id);
            painting.setRoom(room);
            painting.setDesc(desc);
            painting.setRank(rank);
            painting.setArtist(artist);
            painting.setTitle(title);
            painting.setYear(year);

            galleries.add(painting);

        }
        adapter.notifyDataSetChanged();
    }

    private void ShowDialog(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_pop_dialog);
        dialog.setTitle("Update Painting");


        icon = (ImageView) dialog.findViewById(R.id.imgView);

        final EditText painter_name = (EditText) dialog.findViewById(R.id.artist);
        final EditText paint_title = (EditText) dialog.findViewById(R.id.title);
        final EditText room = (EditText) dialog.findViewById(R.id.room);
        final EditText desc = (EditText) dialog.findViewById(R.id.desc);
        final EditText edyear = (EditText) dialog.findViewById(R.id.year);
        final Button btnUpdate = (Button) dialog.findViewById(R.id.btnupdate);


        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);

        int height = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1.75);

        dialog.getWindow().setLayout(width, height);

        dialog.show();


        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(
                        getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Gallery painting = new Gallery();
                    painting.setImg(ImageViewToBlob(icon));
                    painting.setArtist(painter_name.getText().toString());
                    painting.setYear(Integer.parseInt(edyear.getText().toString()));
                    painting.setTitle(paint_title.getText().toString());
                    painting.setDesc(desc.getText().toString());
                    painting.setRoom(room.getText().toString());
                    painting.setId(position);

                    db_helper.update(painting);

                    adapter.notifyDataSetChanged();
                    System.out.println("Update Record");
                    dialog.dismiss();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                updateRecordList();
            }
        });
    }

    private void RateApp(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.rate_pop_layout);
        dialog.setTitle("Rate Painting");


        final RatingBar rate = (RatingBar) dialog.findViewById(R.id.ratingBar);
        final TextView result = (TextView) dialog.findViewById(R.id.result);
        final Button ratebtn = (Button) dialog.findViewById(R.id.ratebtn);


        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);

        int height = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.65);

        dialog.getWindow().setLayout(width, height);

        dialog.show();


        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar rtBar, float rating, boolean fromUser) {
                int r = (int) rating;
                result.setText(String.valueOf(r));
            }
        });


        ratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Gallery painting = new Gallery();
                    painting.setRank(Float.parseFloat(result.getText().toString()));
                    painting.setId(position);

                    // db_helper.(painting);

                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Update Record", Toast.LENGTH_LONG).show();
                    dialog.dismiss();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                UpdateRecordList();
            }
        });

        //   dialog.show();;


    }

    private void UpdateRecordList() {

        Cursor cursor = db_helper.getGallery("SELECT * FROM PAINTING");
        galleries.clear();
        while (cursor.moveToNext()) {


            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String artist = cursor.getString(2);
            String title = cursor.getString(3);
            String desc = cursor.getString(4);
            String room = cursor.getString(5);
            int year = cursor.getInt(6);
            int rank = cursor.getInt(7);

            Gallery painting = new Gallery();

            painting.setImg(img);
            painting.setId(id);
            painting.setRoom(room);
            painting.setDesc(desc);
            painting.setRank(rank);
            painting.setArtist(artist);
            painting.setTitle(title);
            painting.setYear(year);

            galleries.add(painting);


        }
    }

    private byte[] ImageViewToBlob(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytearray = stream.toByteArray();

        return bytearray;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Don't have Permission to access file location", Toast.LENGTH_LONG).show();
            }

            return;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}