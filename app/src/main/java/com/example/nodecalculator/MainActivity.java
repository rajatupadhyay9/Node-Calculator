package com.example.nodecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner dropdown;
    private String operator;
    private TextView result;
    private EditText op1, op2;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL = "http://192.168.0.122:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        dropdown = findViewById(R.id.spinner);
        result = findViewById(R.id.result);
        op1 = findViewById(R.id.operand1);
        op2 = findViewById(R.id.operand2);

        String[] itemlist = new String[]{"+", "-", "*", "/"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                itemlist);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                operator = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.imageButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button) {
            double answer = 0;
            switch (operator) {
                case "+":
                    answer = Double.parseDouble(op1.getText().toString())
                            + Double.parseDouble(op2.getText().toString());
                    break;
                case "-":
                    answer = Double.parseDouble(op1.getText().toString())
                            - Double.parseDouble(op2.getText().toString());
                    break;
                case "*":
                    answer = Double.parseDouble(op1.getText().toString())
                            * Double.parseDouble(op2.getText().toString());
                    break;
                case "/":
                    answer = Double.parseDouble(op1.getText().toString())
                            / Double.parseDouble(op2.getText().toString());
                    break;
            }
            if (answer == (int) answer)
                result.setText("= " + (int) answer);
            else
                result.setText("= " + answer);

            HashMap<String, String> map = new HashMap<>();
            map.put("op1", op1.getText().toString());
            map.put("op2", op2.getText().toString());
            map.put("res", String.valueOf(answer));
            map.put("operator", operator);
            Call<Void> call = retrofitInterface.enterData(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 202) {
                        Toast.makeText(MainActivity.this,
                                "SUCCESS : Data entered into db!", Toast.LENGTH_LONG).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(MainActivity.this,
                                "FAILURE : Invalid data requested to insert into db !!",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        } else if(v.getId() == R.id.imageButton) {
            startActivity(new Intent(MainActivity.this, ShowActivity.class));
        }
    }
}