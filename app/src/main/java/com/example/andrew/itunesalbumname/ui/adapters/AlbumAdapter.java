package com.example.andrew.itunesalbumname.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrew.itunesalbumname.R;
import com.example.andrew.itunesalbumname.model.AlbumDetail;
import com.example.andrew.itunesalbumname.utils.MyDateUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<AlbumDetail> songs = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(songs.get(position));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void setItems(Collection<AlbumDetail> tweets) {
        songs.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clearItems() {
        songs.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.track_name_text_view)
        TextView song_name;
        @BindView(R.id.track_position_text_view)
        TextView number_song;
        @BindView(R.id.track_duration_text_view)
        TextView duration_song;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(AlbumDetail albumSong) {
            song_name.setText(albumSong.getTrackName());
            number_song.setText(String.valueOf(albumSong.getTrackNumber()));
            duration_song.setText(MyDateUtils.millisToMinutes(albumSong.getTrackTimeMillis()));
        }
    }
}
