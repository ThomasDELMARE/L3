package com.example.allumettes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allumettes.view.Allumettes;

public class MainActivity extends AppCompatActivity {

    Allumettes allumettes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allumettes = findViewById(R.id.allumettes);
    }
}