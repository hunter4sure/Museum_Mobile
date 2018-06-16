package com.redeemer.root.museum_mobile.model;

/**
 * Created by root on 6/1/18.
 */

public class Gallery  {


    private int id;
    private byte[] img;
    private String artist;
    private String title;
    private String desc;
    private String room;
    private int year;
    private float rank;


    public Gallery( byte[] img, String artist, String title, String desc, String room, int year) {
        this.img = img;
        this.artist = artist;
        this.title = title;
        this.desc = desc;
        this.room = room;
        this.year = year;
    }

    public Gallery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }
}
