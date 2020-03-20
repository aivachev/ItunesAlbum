package com.example.andrew.itunesalbumname.ui.presenters;

import com.example.andrew.itunesalbumname.MyApplication;
import com.example.andrew.itunesalbumname.network.NetworkInterface;
import com.example.andrew.itunesalbumname.network.RetrofitInstance;
import com.example.andrew.itunesalbumname.ui.interfaces.AlbumPresenterInterface;
import com.example.andrew.itunesalbumname.ui.interfaces.AlbumViewInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumPresenter implements AlbumPresenterInterface {
    private AlbumViewInterface avi;

    public AlbumPresenter(AlbumViewInterface avi) {
        this.avi = avi;
    }

    @Override
    public void getSongBasedOnQuery(int albumId) {
        Disposable disposable = RetrofitInstance.getRetrofitInstance().create(NetworkInterface.class)
                .getSongList(albumId, "song")
                .doOnNext(res -> {
                    Disposable subscribe = MyApplication.getInstance().getMyDatabase().albumDao()
                            .getByCollectionId(res.getResults().get(0).getCollectionId())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(resAlb -> avi.displayAlbumInfo(resAlb),
                                    err -> {
                                        err.printStackTrace();
                                        avi.displayError("Error fetching Album Data");
                                    });
                    ;
                })
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribe(response -> {
                            avi.displaySongInfo(response);
                        },
                        err -> {
                            err.printStackTrace();
                            avi.displayError("Error fetching Song Data");
                        });
    }

    @Override
    public void onDestroy() {
        avi = null;
    }
}