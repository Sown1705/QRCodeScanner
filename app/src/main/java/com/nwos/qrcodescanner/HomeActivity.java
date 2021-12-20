package com.nwos.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nwos.qrcodescanner.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
private ActivityHomeBinding homeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding =ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());


        homeBinding.btnGenerate.setOnClickListener(view->{
            startActivity(new Intent(HomeActivity.this,GenerateActivity.class));
        });
        homeBinding.btnScanner.setOnClickListener(view->{
            startActivity(new Intent(HomeActivity.this,ScanActivity.class));
        });
    }
}