package com.example.andrew.itunesalbumname;

import android.app.SearchManager;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrew.itunesalbumname.db.entitities.Album;
import com.example.andrew.itunesalbumname.ui.adapters.MainAdapter;
import com.example.andrew.itunesalbumname.ui.presenters.MainPresenter;
import com.example.andrew.itunesalbumname.ui.interfaces.MainViewInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainViewInterface{
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerViewAlbums;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.image_empty)
    ImageView emptyPlaceholder;
    @BindView(R.id.empty_text)
    TextView emptyTextview;

    private RecyclerView.Adapter adapter;
    private MainPresenter mainPresenter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        emptyPlaceholder.setVisibility(View.VISIBLE);
        emptyTextview.setVisibility(View.VISIBLE);

        setupMVP();
        setupViews();
        mainPresenter.setupOldData();
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
    public void displayAlbums(List<Album> itunesAlbumsResponse) {
        if (itunesAlbumsResponse.size() != 0) {
            emptyPlaceholder.setVisibility(View.GONE);
            emptyTextview.setVisibility(View.GONE);
            ArrayList alb = new ArrayList<>(itunesAlbumsResponse);
            Collections.sort(alb);
            adapter = new MainAdapter(alb);
            recyclerViewAlbums.setAdapter(adapter);
        } else {
            adapter = new MainAdapter(new ArrayList<>());
            recyclerViewAlbums.setAdapter(adapter);
            emptyPlaceholder.setVisibility(View.VISIBLE);
            emptyTextview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
        mainPresenter = null;
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
