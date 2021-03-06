package com.example.andrew.itunesalbumname.ui.interfaces;

import com.example.andrew.itunesalbumname.db.entitities.Album;
import com.example.andrew.itunesalbumname.model.ItunesAlbumsResponse;

public interface AlbumViewInterface {

    void showToast(String s);
    void displaySongInfo(ItunesAlbumsResponse albumResponse);
    void displayAlbumInfo(Album albumResponse);
    void displayError(String s);
}
