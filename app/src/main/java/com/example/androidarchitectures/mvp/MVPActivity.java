package com.example.androidarchitectures.mvp;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.androidarchitectures.R;
import com.example.androidarchitectures.mvc.CountriesController;
import com.example.androidarchitectures.mvc.MVCActivity;

import java.util.ArrayList;
import java.util.List;

public class MVPActivity extends AppCompatActivity implements CountriesPresenter.View{
    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    Button refreshButton;
    ProgressBar progress;
    private CountriesPresenter countriesPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpactivity);
        setTitle("MVP Activity");
        list = findViewById(R.id.list);
        progress = findViewById(R.id.progress);
        refreshButton = findViewById(R.id.refreshButton);
        countriesPresenter = new CountriesPresenter(this);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVPActivity.this, "item clicked: " + listValues.get(i), Toast.LENGTH_LONG).show();
            }
        });

    }

    public static Intent getIntent(Context context){
        return new Intent(context,MVPActivity.class);
    }

    @Override
    public void setValues(List<String> countries) {
        listValues.clear();
        listValues.addAll(countries);
        refreshButton.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
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
        countriesPresenter.onRetry();
    }
}