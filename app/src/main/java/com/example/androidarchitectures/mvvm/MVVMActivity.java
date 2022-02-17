package com.example.androidarchitectures.mvvm;

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
import androidx.lifecycle.ViewModelProviders;

import com.example.androidarchitectures.R;

import java.util.ArrayList;
import java.util.List;

public class MVVMActivity extends AppCompatActivity {
    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    Button refreshButton;
    ProgressBar progress;
    private CountriesViewModel countriesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvmactivity);
        setTitle("MVVM Activity");
        countriesViewModel = ViewModelProviders.of(this).get(CountriesViewModel.class);
        list = findViewById(R.id.list);
        progress = findViewById(R.id.progress);
        refreshButton = findViewById(R.id.refreshButton);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVVMActivity.this, "item clicked: " + listValues.get(i), Toast.LENGTH_LONG).show();
            }
        });
        observeViewModel();
    }

    private void observeViewModel() {
        countriesViewModel.getCountries().observe(this, countries -> {
            if (countries != null) {
                listValues.clear();
                listValues.addAll(countries);
                list.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            } else {
                list.setVisibility(View.GONE);
            }
            countriesViewModel.getCountryError().observe(this, error -> {
                if (error) {
                    progress.setVisibility(View.GONE);
                    refreshButton.setVisibility(View.VISIBLE);
                } else {
                    refreshButton.setVisibility(View.GONE);
                }
            });
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MVVMActivity.class);
    }

    public void onRetry(View view) {
        refreshButton.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        countriesViewModel.onRefresh();
    }


}