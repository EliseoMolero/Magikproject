package com.example.montxu.magik_repair;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
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
    Button botonfoto, Mmostrar, botonpass, botonnombre, botonapellidos, botonguardar;
    EditText emailtxt, passtxt, compasstxt, nombretxt,apellidostxt;
    TextView textopass, textoemail;
    ImageView fotoperfilview;
    String encodedImage = " ";
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

        botonfoto=(Button)mView.findViewById(R.id.botonfoto);
        botonapellidos=(Button)mView.findViewById(R.id.botonapellidos);
        botonnombre=(Button)mView.findViewById(R.id.botonnombre);
        botonguardar=(Button)mView.findViewById(R.id.botonguardar);
        botonpass=(Button)mView.findViewById(R.id.botonpass);
        fotoperfilview=(ImageView)mView.findViewById(R.id.fotoperfilview);
        Mmostrar=(Button)mView.findViewById(R.id.botonM);        
        
        emailtxt=(EditText)mView.findViewById(R.id.emailtxt);
        passtxt=(EditText)mView.findViewById(R.id.passtxt);
        compasstxt=(EditText)mView.findViewById(R.id.compasstxt);
        nombretxt=(EditText)mView.findViewById(R.id.nombretxt);
        apellidostxt=(EditText)mView.findViewById(R.id.apellidostxt);

        String ids=miUsuario.getIds();
        HttpgetUser tareaAsyncU = new HttpgetUser(ids);
        tareaAsyncU.execute();
        try {
            String[] user = tareaAsyncU.get();
            emailtxt.setText(user[0]);
            passtxt.setText(user[4]);
            compasstxt.setText(user[4]);
            nombretxt.setText(user[2]);
            apellidostxt.setText(user[3]);
            try {
                byte[] decodedString = Base64.decode(user[1], Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                fotoperfilview.setImageBitmap(decodedByte);
            }catch (Exception e){
                System.out.println(e);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //fotoperfilview.setImageURI();
        botonguardar.setEnabled(true);

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
        Mmostrar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        passtxt.setInputType(InputType.TYPE_CLASS_TEXT);
                        compasstxt.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        passtxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        compasstxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        botonguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toastpositivo =Toast.makeText(getContext(),"Datos modificados con Ã©xito", Toast.LENGTH_SHORT);
                Toast toastnegativo =Toast.makeText(getContext(),"No ha podido modificar sus datos", Toast.LENGTH_SHORT);

                String compassstring =String.valueOf(compasstxt.getText());
                String emailstring=String.valueOf(emailtxt.getText());
                String passstring=String.valueOf(passtxt.getText());
                String nombrestring=String.valueOf(nombretxt.getText());
                String apellidosstring=String.valueOf(apellidostxt.getText());
                String imgstring=" ";
                
                if (encodedImage.equals(" ")) {
                    imgstring = miUsuario.getImagenPerfil();
                }else{
                    imgstring = encodedImage;
                }
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
        builder.setTitle("Elige una opciÃ³n");
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
                    fotoperfilview.setImageBitmap(resizedBitmap);
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
                        fotoperfilview.setImageBitmap(resizedBitmap2);
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
            Toast.makeText(getContext(), "Perfil Modificado con exito", Toast.LENGTH_LONG).show();
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
    public class HttpgetUser extends AsyncTask<String, Void, String[]> {

        String ids="";

        public HttpgetUser(String ids) {
            this.ids = ids;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String[] user = operacionesApi.getUser(ids);

            return user;
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

        Toast toastnegativo =Toast.makeText(getContext(),"NO se admiten campos vacÃ­os", Toast.LENGTH_SHORT);
        Toast toastnegativo2 =Toast.makeText(getContext(),"Nombre y/o Apellidos NO pueden ser numÃ©ricos", Toast.LENGTH_SHORT);


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

        Toast toastpositivo =Toast.makeText(getContext(),"ContraseÃ±a vÃ¡lida", Toast.LENGTH_SHORT);
        Toast toastnegativo =Toast.makeText(getContext(),"ContraseÃ±a NO vÃ¡lida", Toast.LENGTH_SHORT);
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
