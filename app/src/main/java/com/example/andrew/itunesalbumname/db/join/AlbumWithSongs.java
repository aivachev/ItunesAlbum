package com.example.andrew.itunesalbumname.db.join;

import androidx.room.Embedded;

import com.example.andrew.itunesalbumname.db.entitities.Album;
import com.example.andrew.itunesalbumname.db.entitities.Song;


public class AlbumWithSongs {
    // @Embedded notation flattens the properties of the User object into the object, preserving encapsulation.
    @Embedded
    public Album album;

    // All fields returned from the Organization table must be prefixed with org_ (i.e. org_id, org_name, etc.)
    @Embedded(prefix="sng_")
    public Song songs;
}
