package com.example.andrew.itunesalbumname.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ItunesAlbumsResponse<T> {
    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<T> results = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public List<T> getResults() {
        return results;
    }

}
