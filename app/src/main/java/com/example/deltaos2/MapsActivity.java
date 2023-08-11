package com.example.deltaos2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.deltaos2.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.MapAccessorsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener {

    TextView description;
    TextView windSpeed;
    TextView humidity;
    TextView pressure;
    TextView currTemperature;
    TextView min_max_temperature;
    TextView locationName;
    TextView lat_long;
    Geocoder geocoder;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final String API_KEY = "c6ecfd655997ea0cd1d16f5b50c89d0f";
    private static final String UNIT = "metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this);

        description = binding.weatherDescriptionTv;
        windSpeed = binding.windSpeedTv;
        humidity = binding.humidityTv;
        pressure = binding.pressureTv;
        currTemperature = binding.tempShowTv;
        min_max_temperature = binding.minMaxTempShowTv;
        locationName = binding.locationShowTv;
        lat_long = binding.latLonShowTv;



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerDragListener(this);

        // Add a marker in Sydney and move the camera
        LatLng NIT_T_LHC = new LatLng(10.7610, 78.8142);

        mMap.addMarker(new MarkerOptions().position(NIT_T_LHC).title("Marker in NITT LHC").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.deltacrown)).anchor(0.5f,0.5f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NIT_T_LHC, 10f));
        start_retrofit_call(10.7610f, 78.8142f);
    }


    @Override
    public void onMarkerDrag(@NonNull Marker marker) {


    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        float latitude =  (float)marker.getPosition().latitude;
        float longitude = (float) marker.getPosition().longitude;
        marker.setAlpha(1);
        try {
            List<Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            if(addresses.size() > 0) {
                Address address = addresses.get(0);
                String place_address = address.getAddressLine(0);
                marker.setTitle(place_address);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        start_retrofit_call(latitude, longitude);


    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {
        marker.setAlpha(0.5f);
        marker.setTitle("");
    }

    public void start_retrofit_call(float latitude, float longitude){
        Call<WeatherResponseModel> getCall = MyRetrofit.getRetrofitInterface().performRequest(latitude, longitude, API_KEY, UNIT);
        getCall.enqueue(new Callback<WeatherResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponseModel> call, @NonNull Response<WeatherResponseModel> response) {

                assert response.body() != null;
                String latitude = String.valueOf(response.body().coord.lat);
                String longitude = String.valueOf(response.body().coord.lon);
                String humidity_value = String.valueOf(response.body().main.humidity);
                String pressure_value= String.valueOf(response.body().main.pressure);
                String temperature = String.valueOf(response.body().main.temp+"℃");
                String maxTemp = String.valueOf(response.body().main.temp_max);
                String minTemp = String.valueOf(response.body().main.temp_min);
                String windSpeed_value = String.valueOf(response.body().wind.speed);
                String location = String.valueOf(response.body().name);
                String weather_description = String.valueOf(response.body().weather.get(0).description);

                locationName.setText(location);
                description.setText(weather_description);
                windSpeed.setText(windSpeed_value);
                humidity.setText(humidity_value);
                pressure.setText(pressure_value);
                currTemperature.setText(temperature);
                String min_max_temperature_string = minTemp+"℃/"+maxTemp+"℃";
                min_max_temperature.setText(min_max_temperature_string);
                locationName.setText(location);
                String lat_lon_String = latitude+"/"+longitude;
                lat_long.setText(lat_lon_String);
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponseModel> call, @NonNull Throwable t) {

                Toast.makeText(MapsActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(addresses.size() > 0) {
                Address address = addresses.get(0);
                String place_address = address.getAddressLine(0);
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in NITT LHC").draggable(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
                start_retrofit_call(10.7610f, 78.8142f);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        start_retrofit_call((float)latLng.latitude, (float) latLng.longitude);
    }
}