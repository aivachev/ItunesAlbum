package com.example.andrew.itunesalbumname.ui.presenters;

import androidx.appcompat.widget.SearchView;

import com.example.andrew.itunesalbumname.MyApplication;
import com.example.andrew.itunesalbumname.db.database.MyDatabase;
import com.example.andrew.itunesalbumname.db.entitities.Album;
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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainPresenter implements MainPresenterInterface {
    private MainViewInterface mvi;

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    public void setupOldData() {
        MyDatabase myDatabase = MyApplication.getInstance().getMyDatabase();
        Disposable subscribe = myDatabase.albumDao().getFew(25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mmm -> {
                    mvi.displayAlbums(mmm);
                });
    }

    @Override
    public void getAlbumsBasedOnQuery(SearchView searchView) {
        getObservableQuery(searchView)
                .filter(s -> !s.equals(""))
                .debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<ItunesAlbumsResponse<Album>>>) s ->
                        RetrofitInstance.getRetrofitInstance()
                                .create(NetworkInterface.class)
                                .getAlbums(s, "music", "album", "albumTerm")
                                .doOnNext(this::saveAllAlbums)
                                .subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private void saveAllAlbums(ItunesAlbumsResponse<Album> alb) {
        MyDatabase myDatabase = MyApplication.getInstance().getMyDatabase();
        myDatabase.albumDao().insertListAlbum(alb.getResults());
    }

    @Override
    public void onDestroy() {
        mvi = null;
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
                mvi.displayAlbums(movieResponse.getResults());
                mvi.hideProgressBar();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                mvi.displayError("Error fetching Album Data, please restart app");
                mvi.hideProgressBar();
            }

            @Override
            public void onComplete() {
                mvi.hideProgressBar();
            }
        };
    }
}
