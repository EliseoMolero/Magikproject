package com.example.montxu.magik_repair;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Pantalla_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment spm;
    RelativeLayout frl;
    int size;
    double longitud;
    double latitud;
    ArrayList<String> direccion = new ArrayList<String>();
    ArrayList<String> descripcion = new ArrayList<String>();
    ArrayList<String> estado = new ArrayList<String>();
    Marker marka;

    final int MY_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spm = SupportMapFragment.newInstance();
        frl = (RelativeLayout) findViewById(R.id.layout_pri);
        setContentView(R.layout.activity_pantalla__principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Usuario usuario = (Usuario)getIntent().getExtras().getSerializable("parametro");
        getIntent().putExtra("usuario", usuario);
        if(mayRequestStoragePermission()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new subirIncidencias()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantalla__principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentManager fmm = getSupportFragmentManager();


        if(spm.isAdded()){
            fmm.beginTransaction().hide(spm).commit();
        }
        if (id == R.id.subirIncidencia) {
            fragmentManager.beginTransaction()
            .replace(R.id.content_frame, new subirIncidencias()).commit();

        } else if (id == R.id.mapaIncidencia) {
            if(!spm.isAdded()) {
                String[] finci=getFinci();
                for (int i = 0; i < finci.length; i++) {
                    Object a = finci[i];
                    String dic_a= a.toString();
                    dic_a = dic_a.replace("\\", "");
                    dic_a = dic_a.replace("\"","");
                    String[] b = dic_a.split(":");
                    this.descripcion.add(b[1].split(",")[0]);
                    this.direccion.add(b[2].split(",")[0]+", Sevilla");
                    this.estado.add(b[3].split(",")[0]);

                }
                this.size=finci.length;
                spm.getMapAsync(this);
                fmm.beginTransaction().add(R.id.map, spm).commit();
            }else{
                String[] finci=getFinci();
                for (int i = 0; i < finci.length; i++) {
                    Object a = finci[i];
                    String dic_a= a.toString();
                    dic_a = dic_a.replace("\\", "");
                    dic_a = dic_a.replace("\"","");
                    String[] b = dic_a.split(":");
                    this.descripcion.add(b[1].split(",")[0]);
                    this.direccion.add(b[2].split(",")[0]+", Sevilla");
                    this.estado.add(b[3].split(",")[0]);

                }
                this.size=finci.length;
                spm.getMapAsync(this);
                fmm.beginTransaction().show(spm).commit();
            }
        } else if (id == R.id.misIncidencias) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new misIncidencias()).commit();
        } else if (id == R.id.miPerfil) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new miPerfil()).commit();
        } else if (id == R.id.contactanos) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new subirIncidencias()).commit();
        } else if (id == R.id.salir) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA)) || (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION))){
            AlertDialog.Builder builder = new AlertDialog.Builder(Pantalla_Principal.this);
            builder.setTitle("Permisos denegados");
            builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, ACCESS_FINE_LOCATION}, MY_PERMISSIONS);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();

        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, ACCESS_FINE_LOCATION}, MY_PERMISSIONS);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Pantalla_Principal.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
            } else {
                showExplanation();
            }
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Pantalla_Principal.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, ACCESS_FINE_LOCATION}, MY_PERMISSIONS);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }


    public String[] getFinci(){
        HttpGetFincidencias tareaAsync = new HttpGetFincidencias();
        tareaAsync.execute();


        try {
            System.out.println("Empieza RECIBIR APP");
            String[] gdata = tareaAsync.get();
            return gdata;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void limpiarArrays(){
            this.direccion.clear();
            this.descripcion.clear();
            this.estado.clear();
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Geocoder geocoder2 = new Geocoder(getApplicationContext());
        for (int i = 0; i <size; i++) {
            List<Address> direcciones2 = null;


            try {
                direcciones2=geocoder2.getFromLocationName(String.valueOf(direccion.get(i)),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            latitud= direcciones2.get(0).getLatitude();
            longitud= direcciones2.get(0).getLongitude();
        if (latitud != 0 && longitud != 0){
            LatLng latLng = new LatLng(latitud, longitud);

            MarkerOptions markerOptions =
                    new MarkerOptions()
                            .position(latLng)
                            .title(direccion.get(i))
                            .snippet(descripcion.get(i));

            marka = googleMap.addMarker(markerOptions);
            CameraUpdate ubica = CameraUpdateFactory.newLatLngZoom(latLng, 12);
            googleMap.animateCamera(ubica);
        }
        }
limpiarArrays();
    }

        private class HttpGetFincidencias extends AsyncTask<String, Void, String[]> {

        String[] inci;


        @Override
        protected String[] doInBackground(String... params) {
            String[] result = operacionesApi.getFullIncidencias();
            inci=result;
            return inci;
        }
    }
}

