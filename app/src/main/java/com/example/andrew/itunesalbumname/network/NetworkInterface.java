package com.example.andrew.itunesalbumname.network;

import com.example.andrew.itunesalbumname.model.AlbumDetail;
import com.example.andrew.itunesalbumname.model.ItunesAlbum;
import com.example.andrew.itunesalbumname.model.ItunesAlbumsResponse;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("search")
    Observable<ItunesAlbumsResponse<ItunesAlbum>> getAlbums(@Query("term") String term, @Query("media") String media,
                                                            @Query("entity") String entity, @Query("attribute") String attribute);

    @GET("lookup")
    Observable<ItunesAlbumsResponse<AlbumDetail>> getSongList(@Query("id") int albumId, @Query("entity") String entity);
}
