package com.example.deltaos2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";


    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

    public static String getBaseUrl (){
        return BASE_URL;
    }
    public static Retrofit getRetrofit(){
        return retrofit;
    }
    public static RetrofitInterface getRetrofitInterface(){
        return retrofitInterface;
    }


}
