package com.example.deltaos2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class WeatherResponseModel {



    @SerializedName("coord")
    Coordinates coord;
    static class Coordinates{
        @SerializedName("lon")
        float lon;

        @SerializedName("lat")
        float lat;
    }
//    "coord": {
//        "lon": 80.2707,
//        "lat": 13.0827
//    }


    @SerializedName("weather")
    ArrayList<WeatherObject> weather;
    static class WeatherObject{
        @SerializedName("description")
        String description;

    }
//    "weather": [
//        {
//        "id": 501,
//        "main": "Rain",
//        "description": "moderate rain",
//        "icon": "10d"
//        }
//    ]


    @SerializedName("wind")
    windObject wind;
    static class windObject{
        @SerializedName("speed")
        float speed;
    }

//    "wind": {
//        "speed": 0.62,
//        "deg": 349,
//        "gust": 1.18
//        }


    @SerializedName("main")
    mainObj main;
    static class mainObj{
        @SerializedName("temp")
        float temp;
        @SerializedName("temp_min")
        float temp_min;
        @SerializedName("temp_max")
        float temp_max;
        @SerializedName("pressure")
        int pressure;
        @SerializedName("humidity")
        int humidity;
    }

    @SerializedName("name")
    String name;
//        "main": {
//        "temp": 298.48,
//        "temp_min": 297.56,
//        "temp_max": 300.05,
//        "pressure": 1015,
//        "humidity": 64,
//        }

//        "name": "Park Town",




}






//        "base": "stations",
//        "main": {
//        "temp": 298.48,
//        "feels_like": 298.74,
//        "temp_min": 297.56,
//        "temp_max": 300.05,
//        "pressure": 1015,
//        "humidity": 64,
//        "sea_level": 1015,
//        "grnd_level": 933
//        },
//        "visibility": 10000,
//        "wind": {
//        "speed": 0.62,
//        "deg": 349,
//        "gust": 1.18
//        },
//        "rain": {
//        "1h": 3.16
//        },
//        "clouds": {
//        "all": 100
//        },
//        "dt": 1661870592,
//        "sys": {
//        "type": 2,
//        "id": 2075663,
//        "country": "IT",
//        "sunrise": 1661834187,
//        "sunset": 1661882248
//        },
//        "timezone": 7200,
//        "id": 3163858,
//        "name": "Zocca",
//        "cod": 200
//        }
//
//
//
