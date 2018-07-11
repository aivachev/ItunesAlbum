package com.example.andrew.itunesalbumname.ui.interfaces;

import com.example.andrew.itunesalbumname.model.ItunesAlbumsResponse;

public interface AlbumViewInterface {

    void showToast(String s);
    void displayAlbumInfo(ItunesAlbumsResponse albumResponse);
    void displayError(String s);
}
