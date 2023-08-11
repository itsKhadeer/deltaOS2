package com.example.deltaos2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {


    @GET("weather")
    Call<WeatherResponseModel> performRequest(@Query("lat") float lat, @Query("lon") float lon, @Query("appid") String API_KEY, @Query("units") String unit);



}


