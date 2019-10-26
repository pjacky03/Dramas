package com.example.mydramas.repository.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DramaRemoteWrapper {
    @SerializedName("data")
    private List<DramaRemote> mData;

    public List<DramaRemote> getDramaRemote() {
        return mData;
    }

    public void setDramaRemote(List<DramaRemote> data) {
        mData = data;
    }
}
