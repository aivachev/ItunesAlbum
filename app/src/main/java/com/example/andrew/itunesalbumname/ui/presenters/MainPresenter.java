package com.example.andrew.itunesalbumname.ui.presenters;

import android.support.v7.widget.SearchView;
import android.util.Log;

import com.example.andrew.itunesalbumname.model.ItunesAlbum;
import com.example.andrew.itunesalbumname.model.ItunesAlbumsResponse;
import com.example.andrew.itunesalbumname.network.NetworkInterface;
import com.example.andrew.itunesalbumname.network.RetrofitInstance;
import com.example.andrew.itunesalbumname.ui.interfaces.MainPresenterInterface;
import com.example.andrew.itunesalbumname.ui.interfaces.MainViewInterface;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainPresenter implements MainPresenterInterface {
    MainViewInterface mvi;
    private String TAG = "MainPresenter";

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    @Override
    public void getAlbumsBasedOnQuery(SearchView searchView) {
        getObservableQuery(searchView)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if(s.equals("")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                })
                .debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<ItunesAlbumsResponse<ItunesAlbum>>>() {
                    @Override
                    public Observable<ItunesAlbumsResponse<ItunesAlbum>> apply(@NonNull String s) throws Exception {
                        return RetrofitInstance.getRetrofitInstance().create(NetworkInterface.class)
                                .getAlbums(s, "music", "album", "albumTerm");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<String> getObservableQuery(SearchView searchView){
        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                mvi.hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                publishSubject.onNext(newText);
                if (!newText.equals("")) {
                    mvi.showProgressBar();
                }
                return true;
            }
        });

        return publishSubject;
    }


    public DisposableObserver<ItunesAlbumsResponse> getObserver(){
        return new DisposableObserver<ItunesAlbumsResponse>() {

            @Override
            public void onNext(@NonNull ItunesAlbumsResponse movieResponse) {
                mvi.displayAlbums(movieResponse);
                mvi.hideProgressBar();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error" + e);
                e.printStackTrace();
                mvi.displayError("Error fetching Album Data, please restart app");
                mvi.hideProgressBar();
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mvi.hideProgressBar();
            }
        };
    }
}
