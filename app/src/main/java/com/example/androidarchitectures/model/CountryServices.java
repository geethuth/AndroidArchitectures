package com.example.androidarchitectures.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryServices {
    public static final String Base_Url="https://restcountries.com/v2/";
    private CountriesApi api;
    public CountryServices(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                        build();
        api = retrofit.create(CountriesApi.class);
    }
    public Single<List<Country>> getCountries(){
        return api.getCountries();
    }
}
