package com.example.nodecalculator;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/enter")
    Call<Void> enterData(@Body HashMap<String, String> map);

    @GET("/retrieve")
    Call<List<HistoryEntry>> getData();
}
