package com.example.andrew.itunesalbumname.ui.presenters;

import android.util.Log;

import com.example.andrew.itunesalbumname.model.AlbumDetailResponse;
import com.example.andrew.itunesalbumname.network.NetworkInterface;
import com.example.andrew.itunesalbumname.network.RetrofitInstance;
import com.example.andrew.itunesalbumname.ui.interfaces.AlbumPresenterInterface;
import com.example.andrew.itunesalbumname.ui.interfaces.AlbumViewInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AlbumPresenter implements AlbumPresenterInterface {
    AlbumViewInterface avi;
    private String TAG = "AlbumPresenter";

    public AlbumPresenter(AlbumViewInterface avi) {
        this.avi = avi;
    }

    @Override
    public void getSongBasedOnQuery(int albumId) {
        RetrofitInstance.getRetrofitInstance().create(NetworkInterface.class)
                .getSongList(albumId, "song")
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribe(getObserver());
    }


    public DisposableObserver<AlbumDetailResponse> getObserver(){
        return new DisposableObserver<AlbumDetailResponse>() {

            @Override
            public void onNext(@NonNull AlbumDetailResponse songResponse) {
                Log.d(TAG,"OnNext"+songResponse.getResults());
                avi.displayAlbumInfo(songResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                avi.displayError("Error fetching Album Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }
}