package com.example.montxu.magik_repair;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import java.io.File;

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
    private Button mLocationButton;
    private Button mSendButton;
    private RelativeLayout mRlView;
    private String mPath;
    private EditText mCajaDes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.subir_incidencias, container, false);
        mOptionButton = (Button) mView.findViewById(R.id.Bfoto);
        mSetImage = (ImageView) mView.findViewById(R.id.fotoView);
        mSendButton = (Button) mView.findViewById(R.id.Benviar);
        mRlView = (RelativeLayout) mView.findViewById(R.id.layoutF);
        mCajaDes = (EditText) mView.findViewById(R.id.cajaDes);
        Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
        R.array.tipos_incidencias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MANDAR TODOO A LA API""!!
                    String descripcion = String.valueOf(mCajaDes.getText());
                    HttpPostIncidencias tareaAsync = new HttpPostIncidencias("full",descripcion);
                    tareaAsync.execute();



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
                if(option[which] == "Camara [o]"){
                    openCamera();
                }else if (option[which] == "Galeria {||}") {
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

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
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

        if(resultCode == RESULT_OK){
            switch (requestCode){
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


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mSetImage.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
                    break;

            }
        }
    }

    private class HttpPostIncidencias extends AsyncTask<String, Void, String>{

        String resultado;
        String imagen;
        String descripcion;
        map_formulario m=new map_formulario();
        Usuario us=new Usuario();

        public HttpPostIncidencias(String imagen, String descripcion) {
            this.imagen = imagen;
            this.descripcion = descripcion;
        }

        @Override
        protected String doInBackground(String... params) {
            String latitud = String.valueOf(m.getLat());
            String descripcion =this.descripcion;
            String direccion="weno";
            String imagen=this.imagen;
            String longitud = String.valueOf(m.getLng());
            String email="antonio@mail.com";
            System.out.println(latitud + " y " + longitud);
            operacionesApi.postIncidencia(descripcion, direccion, imagen, latitud, longitud, email);

            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), resultado, Toast.LENGTH_LONG).show();
        }
    }

}
