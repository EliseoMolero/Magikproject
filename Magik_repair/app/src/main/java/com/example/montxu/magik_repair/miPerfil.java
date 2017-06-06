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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * Created by montxu on 24/05/17.
 */


public class miPerfil extends Fragment{

    View mView;
    Usuario miUsuario;
    Button botonfoto, botonemail, botonpass, botonnombre, botonapellidos, botonguardar;
    EditText emailtxt, passtxt, compasstxt, nombretxt,apellidostxt;
    TextView textopass, textoemail;
    ImageView fotoperfilview;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final int SELECT_PICTURE = 300;
    private final int PHOTO_CODE = 200;
    private static String APP_DIRECTORY = "Repair_Sevilla/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "RepairSevilla";
    private String mPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mi_perfil, container, false );
        Intent i = getActivity().getIntent();
        miUsuario = (Usuario) i.getSerializableExtra("usuario");

        textoemail = (TextView)mView.findViewById(R.id.textoemail);
        textopass = (TextView)mView.findViewById(R.id.textopass);

        textoemail.setVisibility(View.INVISIBLE);

        textopass.setVisibility(View.INVISIBLE);

        botonemail=(Button)mView.findViewById(R.id.botonemail);
        botonfoto=(Button)mView.findViewById(R.id.botonfoto);
        botonapellidos=(Button)mView.findViewById(R.id.botonapellidos);
        botonnombre=(Button)mView.findViewById(R.id.botonnombre);
        botonguardar=(Button)mView.findViewById(R.id.botonguardar);
        botonpass=(Button)mView.findViewById(R.id.botonpass);
        fotoperfilview=(ImageView)mView.findViewById(R.id.fotoperfilview);

        emailtxt=(EditText)mView.findViewById(R.id.emailtxt);
        passtxt=(EditText)mView.findViewById(R.id.passtxt);
        compasstxt=(EditText)mView.findViewById(R.id.compasstxt);
        nombretxt=(EditText)mView.findViewById(R.id.nombretxt);
        apellidostxt=(EditText)mView.findViewById(R.id.apellidostxt);

        emailtxt.setText(miUsuario.getEmail());
        passtxt.setText(miUsuario.getPassword());
        compasstxt.setText(miUsuario.getPassword());
        nombretxt.setText(miUsuario.getNombre());
        apellidostxt.setText(miUsuario.getApellidos());
        fotoperfilview.setImageURI(Uri.parse(miUsuario.getImagenPerfil()));
        botonguardar.setEnabled(true);


        botonemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailtxt.setEnabled(true);
                botonguardar.setEnabled(true);
            }
        });

        botonpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passtxt.setEnabled(true);
                compasstxt.setEnabled(true);
            }
        });

        botonnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombretxt.setEnabled(true);
            }
        });

        botonapellidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apellidostxt.setEnabled(true);
            }
        });

        botonfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                showOptions();
            }
        });

        botonguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toastpositivo =Toast.makeText(getContext(),"Datos modificados con éxito", Toast.LENGTH_SHORT);
                Toast toastnegativo =Toast.makeText(getContext(),"No ha podido modificar sus datos", Toast.LENGTH_SHORT);

                String compassstring =String.valueOf(compasstxt.getText());
                String emailstring=String.valueOf(emailtxt.getText());
                String passstring=String.valueOf(passtxt.getText());
                String nombrestring=String.valueOf(nombretxt.getText());
                String apellidosstring=String.valueOf(apellidostxt.getText());
                String imgstring=miUsuario.getImagenPerfil();
                String idstring=miUsuario.getIds();
                String adminstring=miUsuario.getAdmin();

                HttpPutEditarUsuario tareaAsync=new HttpPutEditarUsuario(emailstring, passstring, imgstring, idstring, adminstring, nombrestring, apellidosstring);
                tareaAsync.execute();
                toastpositivo.show();

                if (validateEmail(emailstring)==true && validateCompass(compassstring,passstring)==true && otherValidates(emailstring,nombrestring,apellidosstring)==true && comprobarExistencia(emailstring)==true){
                    HttpPutEditarUsuario tareaAsync1=new HttpPutEditarUsuario(emailstring, passstring, imgstring, idstring, adminstring, nombrestring, apellidosstring);
                    tareaAsync1.execute();
                    toastpositivo.show();
                }else{
                    toastnegativo.show();
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
                    fotoperfilview.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    fotoperfilview.setImageURI(path);
                    break;

            }
        }
    }

    private class HttpPutEditarUsuario extends AsyncTask<String, Void, String> {

        String resultado;
        String emailstring;
        String passstring;
        String imgstring;
        String idstring;
        String adminstring;
        String nombrestring;
        String apellidosstring;

        public HttpPutEditarUsuario(String emailstring, String passstring, String imgstring, String idstring, String adminstring, String nombrestring, String apellidosstring) {
            this.emailstring = emailstring;
            this.passstring = passstring;
            this.imgstring = imgstring;
            this.idstring = idstring;
            this.adminstring = adminstring;
            this.nombrestring = nombrestring;
            this.apellidosstring = apellidosstring;
        }

        @Override
        protected String doInBackground(String... params) {
            operacionesApi.putUsuario(this.idstring,this.nombrestring, this.apellidosstring, this.emailstring, this.passstring,this.adminstring,this.imgstring);
            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), resultado, Toast.LENGTH_LONG).show();
        }
    }

    private class HttpGetEmails extends AsyncTask<String, Object, String[]> {

        String[] resultado;
        String input;

        public HttpGetEmails(String input) {
            this.input = input;
        }

        public String[] getResultado() {
            return resultado;
        }

        @Override
        protected String[] doInBackground(String... params) {

            String[] result = operacionesApi.getUsuario(this.input);
            this.resultado = result;

            return result;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }

    public boolean comprobarExistencia(String email){

        HttpGetEmails tareaAsync = new HttpGetEmails(email);
        tareaAsync.execute();
        String[] data;

        try {
            data = tareaAsync.get();

            for(int i =0;i>data.length;i++){
                if (data[i].equals(email)){
                    return true;
                }
            }



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        textopass.setVisibility(View.INVISIBLE);
        textoemail.setVisibility(View.VISIBLE);
        return false;

    }

    public boolean otherValidates(String emailstring, String nombrestring, String apellidosstring) {

        Toast toastnegativo =Toast.makeText(getContext(),"NO se admiten campos vacíos", Toast.LENGTH_SHORT);
        Toast toastnegativo2 =Toast.makeText(getContext(),"Nombre y/o Apellidos NO pueden ser numéricos", Toast.LENGTH_SHORT);


        if(isNumeric(nombrestring)==true || isNumeric(apellidosstring)==true){
            toastnegativo2.show();
            return false;
        }else{
            if(emailstring.equals("") || nombrestring.equals("") || apellidosstring.equals("")){
                toastnegativo.show();
                return false;
            }else{
                return true;
            }
        }



    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }


    public static boolean validateEmail(String email) {


        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static boolean validatePass(String pass){

        char clave;
        byte  contNumero = 0, contLetraMay = 0, contLetraMin=0;

        if(pass.equals("")){
            return false;
        }

        for (byte i = 0; i < pass.length(); i++) {
            clave = pass.charAt(i);
            String passValue = String.valueOf(clave);
            if (passValue.matches("[A-Z]")) {
                contLetraMay++;
            } else if (passValue.matches("[a-z]")) {
                contLetraMin++;
            } else if (passValue.matches("[0-9]")) {
                contNumero++;
            }
        }
        if (contLetraMay==0){
            return false;
        }
        if (contLetraMin==0){
            return false;
        }
        if (contNumero==0){
            return false;
        }
        if (pass.length()<8){
            return false;
        }
        return true;
    }

    public boolean validateCompass(String compass, String pass){

        Toast toastpositivo =Toast.makeText(getContext(),"Contraseña válida", Toast.LENGTH_SHORT);
        Toast toastnegativo =Toast.makeText(getContext(),"Contraseña NO válida", Toast.LENGTH_SHORT);
        textoemail.setVisibility(View.INVISIBLE);
        textopass.setVisibility(View.INVISIBLE);

        if (validatePass(pass)==true && validatePass(compass)==true){
            if (pass.equals(compass)){
                toastpositivo.show();
                return true;
            }else {
                toastnegativo.show();
                textopass.setVisibility(View.VISIBLE);
                return false;
            }
        }else {
            toastnegativo.show();
            textopass.setVisibility(View.VISIBLE);
            return false;
        }
    }
}
