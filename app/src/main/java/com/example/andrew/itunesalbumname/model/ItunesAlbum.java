package com.example.andrew.itunesalbumname.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;

public class ItunesAlbum implements Comparable<ItunesAlbum>{
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("collectionId")
    @Expose
    private Integer collectionId;
    @SerializedName("collectionCensoredName")
    @Expose
    private String collectionCensoredName;
    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("wrapperType")
    @Expose
    private String wrapperType;
    @SerializedName("collectionType")
    @Expose
    private String collectionType;
    @SerializedName("artistId")
    @Expose
    private Integer artistId;
    @SerializedName("amgArtistId")
    @Expose
    private Integer amgArtistId;
    @SerializedName("collectionName")
    @Expose
    private String collectionName;
    @SerializedName("artistViewUrl")
    @Expose
    private String artistViewUrl;
    @SerializedName("collectionViewUrl")
    @Expose
    private String collectionViewUrl;
    @SerializedName("artworkUrl60")
    @Expose
    private String artworkUrl60;
    @SerializedName("collectionPrice")
    @Expose
    private Double collectionPrice;
    @SerializedName("collectionExplicitness")
    @Expose
    private String collectionExplicitness;
    @SerializedName("trackCount")
    @Expose
    private Integer trackCount;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("primaryGenreName")
    @Expose
    private String primaryGenreName;
    @SerializedName("contentAdvisoryRating")
    @Expose
    private String contentAdvisoryRating;


    public String getArtistName() {
        return artistName;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int compareTo(ItunesAlbum other) {
        return getCollectionCensoredName().compareTo(other.getCollectionCensoredName());
    }


    public String getWrapperType() {
        return wrapperType;
    }
    public String getCollectionType() {
        return collectionType;
    }
    public Integer getArtistId() {
        return artistId;
    }
    public Integer getAmgArtistId() {
        return amgArtistId;
    }
    public String getCollectionName() {
        return collectionName;
    }
    public String getArtistViewUrl() {
        return artistViewUrl;
    }
    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }
    public String getArtworkUrl60() {
        return artworkUrl60;
    }
    public Double getCollectionPrice() {
        return collectionPrice;
    }
    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }
    public Integer getTrackCount() {
        return trackCount;
    }
    public String getCopyright() {
        return copyright;
    }
    public String getCountry() {
        return country;
    }
    public String getCurrency() {
        return currency;
    }
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }
    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }
}
