package com.example.andrew.itunesalbumname.ui.interfaces;

import com.example.andrew.itunesalbumname.db.entitities.Album;

import java.util.List;

public interface MainViewInterface {

    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayAlbums(List<Album> itunesAlbumsResponse);
    void displayError(String s);
    void hideKeyboard();
}
