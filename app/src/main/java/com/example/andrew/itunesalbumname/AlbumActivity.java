package com.example.andrew.itunesalbumname;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrew.itunesalbumname.model.AlbumDetail;
import com.example.andrew.itunesalbumname.model.AlbumDetailResponse;
import com.example.andrew.itunesalbumname.ui.presenters.AlbumPresenter;
import com.example.andrew.itunesalbumname.ui.interfaces.AlbumViewInterface;
import com.example.andrew.itunesalbumname.ui.adapters.AlbumAdapter;
import com.example.andrew.itunesalbumname.utils.MyDateUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumActivity extends AppCompatActivity implements AlbumViewInterface {
    @BindView(R.id.song_recycler_view)
    RecyclerView songsRecyclerView;
    @BindView(R.id.album_name_text_view)
    TextView album_name;
    @BindView(R.id.album_image_view)
    ImageView albumImageView;
    @BindView(R.id.artist_name_text_view)
    TextView artist_name;
    @BindView(R.id.copyright_text_view)
    TextView copyright;
    @BindView(R.id.release_date_text_view)
    TextView release_date;

    private AlbumAdapter albumAdapter;
    private AlbumPresenter albumPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        setupMVP();
        setupViews();

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
    public void displayAlbumInfo(AlbumDetailResponse albumResponse) {
        albumAdapter = new AlbumAdapter(); //create adapter for recyclerview
        albumAdapter.setItems(albumResponse.getResults().subList(1,albumResponse.getResultCount()));
        displayAlbumInfo(albumResponse.getResults().get(0));
        songsRecyclerView.setAdapter(albumAdapter);
    }

    private void displayAlbumInfo(AlbumDetail alb) {
        album_name.setText(alb.getCollectionCensoredName());
        artist_name.setText(alb.getArtistName());
        release_date.setText(MyDateUtils.getFormattedDate(alb.getReleaseDate()));
        copyright.setText(alb.getCopyright());
        Picasso.get().load(alb.getArtworkUrl100()).into(albumImageView);
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }
}