package com.example.andrew.itunesalbumname.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrew.itunesalbumname.R;
import com.example.andrew.itunesalbumname.AlbumActivity;
import com.example.andrew.itunesalbumname.model.ItunesAlbum;
import com.example.andrew.itunesalbumname.utils.MyDateUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<ItunesAlbum> itunesAlbum;
    private Context context;

    public MainAdapter(ArrayList<ItunesAlbum> itunesAlbum, Context context) {
        this.itunesAlbum = itunesAlbum;
        this.context = context;
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
        holder.album_name.setText(itunesAlbum.get(position).getCollectionCensoredName());
        holder.artist_name.setText(itunesAlbum.get(position).getArtistName());
        holder.release_year.setText(MyDateUtils.getFormattedDate(itunesAlbum.get(position).getReleaseDate()));
        Picasso.get().load(itunesAlbum.get(position).getArtworkUrl100()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AlbumActivity.class);
                intent.putExtra("ALBUM_ID", itunesAlbum.get(position).getCollectionId());
                view.getContext().startActivity(intent);
            }
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