package com.example.andrew.itunesalbumname.db.entitities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Album implements Comparable<Album>{
    @PrimaryKey(autoGenerate=true)
    public Long uid;

    @SerializedName("collectionCensoredName")
    @ColumnInfo(name = "album_name")
    private String album_name;

    @SerializedName("artistName")
    @ColumnInfo(name = "artist_name")
    private String artist_name;

    @SerializedName("releaseDate")
    @ColumnInfo(name = "release_year")
    private String release_year;

    @SerializedName("artworkUrl100")
    @ColumnInfo(name = "album_image")
    private String album_image;

    @SerializedName("collectionId")
    @ColumnInfo(name = "collection_id")
    private Integer collectionId;

    public int compareTo(Album other) {
        return getAlbum_name().compareTo(other.getAlbum_name());
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public String getAlbum_image() {
        return album_image;
    }

    public void setAlbum_image(String album_image) {
        this.album_image = album_image;
    }
}
