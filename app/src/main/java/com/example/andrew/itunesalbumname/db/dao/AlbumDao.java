package com.example.andrew.itunesalbumname.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.andrew.itunesalbumname.db.entitities.Album;
import com.example.andrew.itunesalbumname.db.join.AlbumWithSongs;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM Album where uid = :id")
    Album getById(Long id);

    @Query("SELECT * FROM Album where collection_id = :id")
    Maybe<Album> getByCollectionId(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertAlbum(Album album);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListAlbum(List<Album> album);

    @Delete
    void deleteAlbum(Album album);

    @Query("SELECT * FROM Album")
    Maybe<List<Album>> getAll();

    @Query("SELECT * FROM Album LIMIT :amount")
    Maybe<List<Album>> getFew(int amount);

    // declare inner join here
    @Query("SELECT Album.*, Song.uid as sng_id, Song.first_name AS sng_name, Song.duration_song AS sng_duration," +
            "Song.number_song AS sng_nbmr FROM Album INNER JOIN Song " +
            "ON Song.album_id = Album.uid WHERE Album.uid = :id")
    public AlbumWithSongs getWithAlbById(int id);
}
