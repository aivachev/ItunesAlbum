package com.example.andrew.itunesalbumname.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.andrew.itunesalbumname.db.entitities.Song;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface SongDao {
    @Query("SELECT * FROM Song where uid = :id")
    Song getById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertSong(Song song);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListSong(List<Song> song);

    @Delete
    void deleteSong(Song song);

    @Query("SELECT * FROM Song")
    Maybe<List<Song>> getAll();

    @Query("SELECT * FROM Song LIMIT :amount")
    Maybe<List<Song>> getFew(int amount);


}
