package com.example.andrew.itunesalbumname.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.andrew.itunesalbumname.db.dao.AlbumDao;
import com.example.andrew.itunesalbumname.db.dao.SongDao;
import com.example.andrew.itunesalbumname.db.entitities.Album;
import com.example.andrew.itunesalbumname.db.entitities.Song;

@Database(entities={Album.class, Song.class}, version=3)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
    public abstract SongDao songDao();

    // Database name
    public static final String NAME = "AlbumDataBase";
}
