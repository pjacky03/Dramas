package com.example.mydramas.repository.network;

import com.google.gson.annotations.SerializedName;

// Each drama item in json file.
public class DramaRemote {

    @SerializedName("drama_id")
    private Integer dramaId;

    @SerializedName("name")
    private String name;

    @SerializedName("total_views")
    private String totalViews;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("rating")
    private String rating;

    public Integer getDramaId() {
        return dramaId;
    }

    public void setDramaId(Integer dramaId) {
        this.dramaId = dramaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(String totalViews) {
        this.totalViews = totalViews;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
