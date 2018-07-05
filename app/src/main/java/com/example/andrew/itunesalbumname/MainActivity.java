package com.example.andrew.itunesalbumname;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrew.itunesalbumname.model.ItunesAlbumsResponse;
import com.example.andrew.itunesalbumname.ui.adapters.MainAdapter;
import com.example.andrew.itunesalbumname.ui.presenters.MainPresenter;
import com.example.andrew.itunesalbumname.ui.interfaces.MainViewInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainViewInterface{
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerViewAlbums;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String TAG = "MainActivity";
    private RecyclerView.Adapter adapter;
    private MainPresenter mainPresenter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupMVP();
        setupViews();
        showToast("Just empty screen before you start searching");
    }

    private void setupMVP() {
        mainPresenter = new MainPresenter(this);
    }

    private void setupViews() {
        recyclerViewAlbums.setHasFixedSize(true);
        recyclerViewAlbums.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayAlbums(ItunesAlbumsResponse itunesAlbumsResponse) {
        if(itunesAlbumsResponse != null) {
            itunesAlbumsResponse.sort();
            adapter = new MainAdapter(new ArrayList<>(itunesAlbumsResponse.getResults()), MainActivity.this);
            recyclerViewAlbums.setAdapter(adapter);
        }else{
            Log.d(TAG,"Albums response null");
        }
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }

    @Override
    public void hideKeyboard() {
        searchView.clearFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_list, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        mainPresenter.getAlbumsBasedOnQuery(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
