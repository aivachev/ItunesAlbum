package com.example.andrew.itunesalbumname.ui.interfaces;

import com.example.andrew.itunesalbumname.model.AlbumDetailResponse;

public interface AlbumViewInterface {

    void showToast(String s);
    void displayAlbumInfo(AlbumDetailResponse albumResponse);
    void displayError(String s);
}
