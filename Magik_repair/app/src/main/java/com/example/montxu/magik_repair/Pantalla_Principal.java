package com.example.montxu.magik_repair;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Pantalla_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment spm;
    RelativeLayout frl;
    Button mOptionButton;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        spm.getMapAsync(this);
        if(mayRequestStoragePermission()) {
            Toast.makeText(getApplicationContext(), "Bienvenido a repair sevilla", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Permisos No Aceptados", Toast.LENGTH_LONG).show();

        }

        //mLocationButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //map.ubicacionIncidencia();

          //  }
        //});
        //mSendButton.setOnClickListener(new View.OnClickListener() {
           // @Override
         //   public void onClick(View v) {
          //      Toast.makeText(getApplicationContext(), "Se envio su incidencia,\n   ¡muchas gracias!", Toast.LENGTH_LONG).show();
        //    }
        //});

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
            Toast.makeText(getApplicationContext(), "AQUI SE LE PUE METER ALGO ", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentManager fmm = getSupportFragmentManager();
        Usuario usuario = (Usuario)getIntent().getExtras().getSerializable("parametro");
        getIntent().putExtra("usuario", usuario);

        if(spm.isAdded()){
            fmm.beginTransaction().hide(spm).commit();
        }
        if (id == R.id.subirIncidencia) {
            fragmentManager.beginTransaction()
            .replace(R.id.content_frame, new subirIncidencias()).commit();

        } else if (id == R.id.mapaIncidencia) {
            if(!spm.isAdded()) {
                fmm.beginTransaction().add(R.id.map, spm).commit();
            }else{
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
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new subirIncidencias()).commit();
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng i1 = new LatLng(37.387094, -5.971877);
        LatLng i2 = new LatLng(37.379156, -5.972904);
        LatLng i3 = new LatLng(37.380166, -5.971464);
        CameraUpdate ubica = CameraUpdateFactory.newLatLngZoom(i2, 14);
    }

}
