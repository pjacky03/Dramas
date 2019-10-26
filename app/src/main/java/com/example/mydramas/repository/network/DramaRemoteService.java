package com.example.mydramas.repository.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DramaRemoteService {
    @GET ("interview/dramas-sample.json")
    Call<DramaRemoteWrapper> loadDramas();
}
