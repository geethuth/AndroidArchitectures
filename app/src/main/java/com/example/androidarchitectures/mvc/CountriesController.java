package com.example.androidarchitectures.mvc;

import android.util.Log;

import com.example.androidarchitectures.model.Country;
import com.example.androidarchitectures.model.CountryServices;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesController {
    private MVCActivity view;
    private CountryServices services;

    public CountriesController(MVCActivity view){
        this.view=view;
        services = new CountryServices();
        fetchCountries();
    }

    private void fetchCountries() {
        services.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> countries) {
                        Log.d("testingarray","country:"+countries.size());
                        List<String> countryNames = new ArrayList<>();
                        for(Country c:countries){
                            countryNames.add(c.countryName);
                            Log.d("testingarray","country check:"+c.countryName);

                        }
                        view.setValues(countryNames);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("testingarray","country:"+throwable);
                        view.getError();
                    }
                });
    }

    public void onRetry(){
        fetchCountries();
    }
}
