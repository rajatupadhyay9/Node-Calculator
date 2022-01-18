package com.example.nodecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<HistoryEntry> data;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL = "http://192.168.0.122:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<List<HistoryEntry>> call = retrofitInterface.getData();
        call.enqueue(new Callback<List<HistoryEntry>>() {
            @Override
            public void onResponse(Call<List<HistoryEntry>> call, Response<List<HistoryEntry>> response) {
                data = response.body();
                adapter = new RecyclerViewAdapter(data);
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<HistoryEntry>> call, Throwable t) {
                Toast.makeText(ShowActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}