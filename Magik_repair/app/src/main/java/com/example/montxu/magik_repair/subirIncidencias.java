package com.example.montxu.magik_repair;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by montxu on 24/05/17.
 */


public class subirIncidencias extends Fragment {

    View mView;
    private static String APP_DIRECTORY = "Repair_Sevilla/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "RepairSevilla";

    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private Button mOptionButton;
    private Button mSendButton;
    private String mPath;
    private EditText mCajaDes;
    private EditText mCajaDir;
    String lat;
    String lng;
    String encodedImage = "Null";
    String email ="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.subir_incidencias, container, false);
        mOptionButton = (Button) mView.findViewById(R.id.Bfoto);
        mSetImage = (ImageView) mView.findViewById(R.id.fotoView);
        mSendButton = (Button) mView.findViewById(R.id.Benviar);
        mCajaDes = (EditText) mView.findViewById(R.id.cajaDes);
        mCajaDir = (EditText) mView.findViewById(R.id.cajaDir);
        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });
        Intent i = getActivity().getIntent();
        Usuario miU;
        miU = (Usuario) i.getSerializableExtra("usuario");
        String ids=miU.getIds();
        HttpgetEmail tareaAsync2 = new HttpgetEmail(ids);
        tareaAsync2.execute();
        try {
            email = tareaAsync2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(email);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MANDAR TODOO A LA API""!!
                String descripcion = String.valueOf(mCajaDes.getText());
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                }
                LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    lat = String.valueOf(location.getLatitude());
                    lng = String.valueOf(location.getLongitude());
                    Geocoder geocoder = new Geocoder(getContext());
                    List<Address> direcciones = null;
                    try {
                        direcciones = geocoder.getFromLocation(location.getLatitude(), location.getLongitude() ,1);
                    } catch (Exception e) {
                        Log.d("Error", "Error en geocoder:"+e.toString());
                    }
                    if(direcciones != null && direcciones.size() > 0 ){
                        Address direccion = direcciones.get(0);
                        String dic = direccion.getAddressLine(0);
                        //String direccionText = String.format("%s %s %s",
                        //        direccion.getMaxAddressLineIndex() > 0 ? direccion.getAddressLine(0) : "",
                        //        direccion.getLocality(),
                        //        direccion.getCountryName()
                        //);
                        if (encodedImage.equals("Null")){
                            Toast.makeText(getContext(), "Seleccione una imagen para su incidencia", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if (descripcion!="") {

                                HttpPostIncidencias tareaAsync = new HttpPostIncidencias(email, encodedImage, descripcion, lat, lng, dic, " ");
                                tareaAsync.execute();                                

                            }
                            else{
                                Toast.makeText(getContext(), "Describa el tipo de incidencia", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                }else if(String.valueOf(mCajaDir.getText())==""){
                    Toast.makeText(getContext(), "La Direccion selecionada no es valida", Toast.LENGTH_LONG).show();
                }
                else{
                    Geocoder geocoder2 = new Geocoder(getContext());
                    List<Address> direcciones2;
                    try {
                        direcciones2=geocoder2.getFromLocationName(String.valueOf(mCajaDir.getText()),1);
                        double latD= direcciones2.get(0).getLatitude();
                        double lngD= direcciones2.get(0).getLongitude();
                        if (encodedImage.equals("Null")){
                            Toast.makeText(getContext(), "Seleccione una imagen para su incidencia", Toast.LENGTH_LONG).show();
                        }
                        else{
                            String lat = String.valueOf(latD);
                            String lng = String.valueOf(lngD);
                            if (descripcion.equals("")) {

                                    HttpPostIncidencias tareaAsync = new HttpPostIncidencias(email, encodedImage, descripcion, lat, lng, String.valueOf(mCajaDir.getText()), " ");
                                    tareaAsync.execute();
                            }else{
                                    Toast.makeText(getContext(), "Describa el tipo de incidencia", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "No se a podido encontrar su direccion", Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(), "No se a podido encontrar su direccion", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return mView;
    }

    private void showOptions() {
        final CharSequence[] option = {"Camara [o]", "Galeria {||}", "Cancelar X"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (option[which] == "Camara [o]") {
                    openCamera();
                } else if (option[which] == "Galeria {||}") {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecciona la foto de la incidencia"), SELECT_PICTURE);
                } else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if (isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(getContext(),
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });

                     Bitmap bm = BitmapFactory.decodeFile(mPath);
                    int width = bm.getWidth();
                    int height = bm.getHeight();
                    int newWidth = 500;
                    int newHeight = 500;

                    // calculamos el escalado de la imagen destino
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;

                    // para poder manipular la imagen
                    // debemos crear una matriz

                    Matrix matrix = new Matrix();
                    // resize the Bitmap
                    matrix.postScale(scaleWidth, scaleHeight);

                    // volvemos a crear la imagen con los nuevos valores
                    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0,
                            width, height, matrix, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    this.encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    mSetImage.setImageBitmap(resizedBitmap);
                    break;
                case SELECT_PICTURE:
                  Uri path = data.getData();
                    try {
                        Bitmap imagen = getBitmapFromUri (path);
                        int width2 = imagen.getWidth();
                        int height2 = imagen.getHeight();
                        int newWidth2 = 500;
                        int newHeight2 = 500;

                        // calculamos el escalado de la imagen destino
                        float scaleWidth2 = ((float) newWidth2) / width2;
                        float scaleHeight2 = ((float) newHeight2) / height2;

                        // para poder manipular la imagen
                        // debemos crear una matriz

                        Matrix matrix2 = new Matrix();
                        // resize the Bitmap
                        matrix2.postScale(scaleWidth2, scaleHeight2);

                        // volvemos a crear la imagen con los nuevos valores
                        Bitmap resizedBitmap2 = Bitmap.createBitmap(imagen, 0, 0,
                                width2, height2, matrix2, true);
                        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                        resizedBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2); //bm is the bitmap object
                        byte[] b2 = baos2.toByteArray();
                        this.encodedImage = Base64.encodeToString(b2, Base64.DEFAULT);
                        mSetImage.setImageBitmap(resizedBitmap2);
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }
    }


    private Bitmap getBitmapFromUri ( Uri uri ) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContext().getContentResolver (). openFileDescriptor ( uri , "r" );
        FileDescriptor fileDescriptor = parcelFileDescriptor . getFileDescriptor ();
        Bitmap image = BitmapFactory . decodeFileDescriptor ( fileDescriptor );
        parcelFileDescriptor . close ();
        return image ;
    }

    public class HttpPostIncidencias extends AsyncTask<String, Void, String> {

        String resultado;
        String imagen;
        String descripcion;
        String lat;
        String lng;
        String direccion;
        String estado;
        String email;

        public HttpPostIncidencias(String email, String imagen, String descripcion, String lat, String lng, String direccion, String estado ) {
            this.imagen = imagen;
            this.descripcion = descripcion;
            this.lat = lat;
            this.lng = lng;
            this.direccion = direccion;
            this.estado = estado;
            this.email=email;
        }

        @Override
        protected String doInBackground(String... params) {

            String latitud = lng;
            String descripcion =this.descripcion;
            String direccion=this.direccion;
            String imagen=this.imagen;
            String longitud = lat;

            Intent i = getActivity().getIntent();
            Usuario miUsuario = (Usuario) i.getSerializableExtra("usuario");
            String email=miUsuario.getEmail();
            String estado = "No se ha revisado su incidencia todavia";

            operacionesApi.postIncidencia(descripcion, direccion, imagen, latitud, longitud, email, estado);

            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), "Su incidencia se envio", Toast.LENGTH_LONG).show();
        }
    }

public class HttpgetEmail extends AsyncTask<String, Void, String> {

        String ids;

        public HttpgetEmail(String ids) {
            this.ids = ids;
        }

        @Override
        protected String doInBackground(String... params) {
            String correo;
            correo=operacionesApi.getCorreo(ids);
            return correo;
        }

        @Override
        protected void onPostExecute(String result) {}
    }

}
