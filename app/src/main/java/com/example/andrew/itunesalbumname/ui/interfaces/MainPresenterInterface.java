package com.example.andrew.itunesalbumname.ui.interfaces;

import androidx.appcompat.widget.SearchView;

public interface MainPresenterInterface {

    void setupOldData();
    void getAlbumsBasedOnQuery(SearchView searchView);
    void onDestroy();
}
