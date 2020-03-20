package com.example.andrew.itunesalbumname.ui.adapters;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrew.itunesalbumname.R;
import com.example.andrew.itunesalbumname.AlbumActivity;
import com.example.andrew.itunesalbumname.db.entitities.Album;
import com.example.andrew.itunesalbumname.utils.MyDateUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<Album> itunesAlbum;

    public MainAdapter(ArrayList<Album> itunesAlbum) {
        this.itunesAlbum = itunesAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.album_name.setText(itunesAlbum.get(position).getAlbum_name());
        holder.artist_name.setText(itunesAlbum.get(position).getArtist_name());
        holder.release_year.setText(MyDateUtils.getFormattedDate(itunesAlbum.get(position).getRelease_year()));
        Picasso.get().load(itunesAlbum.get(position).getAlbum_image()).into(holder.imageView);
        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AlbumActivity.class);
            intent.putExtra("ALBUM_ID", itunesAlbum.get(position).getCollectionId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itunesAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.album_name)
        TextView album_name;
        @BindView(R.id.artist_name)
        TextView artist_name;
        @BindView(R.id.release_year)
        TextView release_year;
        @BindView(R.id.card_row)
        CardView cardView;
        @BindView(R.id.album_image)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}