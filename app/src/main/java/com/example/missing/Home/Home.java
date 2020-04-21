package com.example.missing.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.missing.Categories.CategoryActivity;
import com.example.missing.R;
import com.example.missing.Search.SearchActivity;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void Add(View view) {
        startActivity(new Intent(this, CategoryActivity.class));
    }

    public void search(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }
}
