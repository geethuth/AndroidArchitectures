package com.example.androidarchitectures.mvp;

import android.util.Log;
import android.view.View;

import com.example.androidarchitectures.model.Country;
import com.example.androidarchitectures.model.CountryServices;
import com.example.androidarchitectures.mvc.MVCActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesPresenter {
    private View view;
    private CountryServices services;

    public CountriesPresenter(View view){
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

    public interface View {
        void setValues(List<String> countries);
        void getError();
    }
}
