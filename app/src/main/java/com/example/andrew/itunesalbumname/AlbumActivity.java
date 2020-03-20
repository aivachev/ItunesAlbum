package com.example.andrew.itunesalbumname;

import com.example.andrew.itunesalbumname.db.entitities.Album;
import com.example.andrew.itunesalbumname.model.ItunesAlbumsResponse;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrew.itunesalbumname.ui.presenters.AlbumPresenter;
import com.example.andrew.itunesalbumname.ui.interfaces.AlbumViewInterface;
import com.example.andrew.itunesalbumname.ui.adapters.AlbumAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class AlbumActivity extends AppCompatActivity implements AlbumViewInterface, AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.song_recycler_view)
    RecyclerView songsRecyclerView;
    @BindView(R.id.album_name_text_view)
    TextView album_name;
    @BindView(R.id.album_image_view)
    ImageView albumImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.artist_name_text_view)
    TextView artist_name;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.album_top_image)
    ImageView albumTopImage;

    private AlbumAdapter albumAdapter;
    private AlbumPresenter albumPresenter;
    private int mMaxScrollSize;
    private static final int PERCENTAGE_TO_ANIMATE_ALBUM = 20;
    private boolean mIsAlbumShown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        setupMVP();
        setupViews();

        appBarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appBarLayout.getTotalScrollRange();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt("ALBUM_ID");
            getSongList(id);
        }
    }

    private void getSongList(int id) {
        albumPresenter.getSongBasedOnQuery(id);
    }

    private void setupViews() {
        songsRecyclerView.setHasFixedSize(true);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupMVP(){
        albumPresenter = new AlbumPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                //return to previous screen
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_share:
                setShareIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setShareIntent() {
        Toast.makeText(this, "placeholder share media", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(AlbumActivity.this, s ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displaySongInfo(ItunesAlbumsResponse albumResponse) {
        albumAdapter = new AlbumAdapter(); //create adapter for recyclerview
        albumAdapter.setItems(albumResponse.getResults().subList(1,albumResponse.getResultCount()));
        songsRecyclerView.setAdapter(albumAdapter);
    }

    @Override
    public void displayAlbumInfo(Album alb) {
        album_name.setText(alb.getAlbum_name());
        artist_name.setText(alb.getArtist_name());
        Picasso.get()
                .load(alb.getAlbum_image())
                .transform(new BlurTransformation(getApplicationContext(), 5, 2))
                .into(albumImageView);
        Picasso.get().load(alb.getAlbum_image()).into(albumTopImage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        albumPresenter.onDestroy();
        albumPresenter = null;
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;
        if (percentage >= PERCENTAGE_TO_ANIMATE_ALBUM && mIsAlbumShown) {
            mIsAlbumShown = false;
            albumTopImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }
        if (percentage <= PERCENTAGE_TO_ANIMATE_ALBUM && !mIsAlbumShown) {
            mIsAlbumShown = true;
            albumTopImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }


}