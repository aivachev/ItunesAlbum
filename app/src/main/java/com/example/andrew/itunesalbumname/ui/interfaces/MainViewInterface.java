package com.example.andrew.itunesalbumname.ui.interfaces;

import com.example.andrew.itunesalbumname.model.ItunesAlbumsResponse;

public interface MainViewInterface {

    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayAlbums(ItunesAlbumsResponse itunesAlbumsResponse);
    void displayError(String s);
    void hideKeyboard();
}
