package com.example.androidarchitectures.mvc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidarchitectures.R;

import java.util.ArrayList;
import java.util.List;

public class MVCActivity extends AppCompatActivity {
    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    Button refreshButton;
    ProgressBar progress;
    private CountriesController countriesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvcactivity);
        setTitle("MVC Activity");
        list = findViewById(R.id.list);
        progress = findViewById(R.id.progress);
        refreshButton = findViewById(R.id.refreshButton);
        countriesController = new CountriesController(this);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVCActivity.this, "item clicked: " + listValues.get(i), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setValues(List<String> values) {
        listValues.clear();
        listValues.addAll(values);
        refreshButton.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MVCActivity.class);
    }

    public void getError() {
        Toast.makeText(this, getString(R.string.err_msg), Toast.LENGTH_LONG).show();
        refreshButton.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
    }

    public void onRetry(View view) {
        refreshButton.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        countriesController.onRetry();
    }
}