package com.example.andrew.itunesalbumname.db.entitities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(entity=Album.class, parentColumns="uid", childColumns="album_id"))
public class Song {
    @PrimaryKey(autoGenerate=true)
    public Long uid;

    @SerializedName("trackName")
    @ColumnInfo(name = "first_name")
    private String song_name;

    @SerializedName("trackNumber")
    @ColumnInfo(name = "number_song")
    private Integer number_song;

    @SerializedName("trackTimeMillis")
    @ColumnInfo(name = "duration_song")
    private Integer duration_song;

    @SerializedName("collectionId")
    @ColumnInfo(name = "collection_id")
    private Integer collectionId;

    @ColumnInfo(name = "album_id")
    public Integer album_id;

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public Integer getNumber_song() {
        return number_song;
    }

    public void setNumber_song(Integer number_song) {
        this.number_song = number_song;
    }

    public Integer getDuration_song() {
        return duration_song;
    }

    public void setDuration_song(Integer duration_song) {
        this.duration_song = duration_song;
    }
}
