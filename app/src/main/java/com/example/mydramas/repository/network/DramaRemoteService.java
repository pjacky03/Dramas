package com.example.mydramas.repository.network;

import retrofit2.Call;
import retrofit2.http.GET;

// Retrofit: The service to get data
public interface DramaRemoteService {
    @GET ("interview/dramas-sample.json")
    Call<DramaRemoteWrapper> loadDramas();
}
