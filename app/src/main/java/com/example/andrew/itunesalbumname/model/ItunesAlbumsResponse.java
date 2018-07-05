package com.example.andrew.itunesalbumname.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ItunesAlbumsResponse {
    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<ItunesAlbum> results = null;

    public void sort() {Collections.sort(results);}

    public Integer getResultCount() {
        return resultCount;
    }

    public List<ItunesAlbum> getResults() {
        return results;
    }

}
