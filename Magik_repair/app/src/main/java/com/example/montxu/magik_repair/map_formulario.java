package com.example.montxu.magik_repair;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by montxu on 24/05/17.
 */

public class map_formulario extends Fragment implements OnMapReadyCallback {


    GoogleMap mMap;
    MapView mMapview;
    View nView;
    Marker marka;
    double lat = 0.0;
    double lng = 0.0;

    public map_formulario() {
        // Required empty public constructor
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mapaformulario, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapFragment fragmentM= (MapFragment)getChildFragmentManager().findFragmentById(R.id.mapForm);
        fragmentM.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        ubicacionIncidencia();
    }


    public void agregarMarka(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate ubica = CameraUpdateFactory.newLatLngZoom(coordenadas, 18);
        if (marka != null) {
            marka.remove();
        }
        marka = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Posicion de la incidencia"));
        mMap.animateCamera(ubica);
    }

    public void actualizarUbica(Location location) {
        if (location != null) {
            this.lat = location.getLatitude();
            this.lng = location.getLongitude();
            agregarMarka(lat, lng);
        }
    }

    LocationListener loclis = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbica(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void ubicacionIncidencia() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbica(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, loclis);

    }


}

