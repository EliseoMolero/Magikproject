package com.example.montxu.magik_repair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by usuario on 30/05/2017.
 */

public class nuevoUsuario extends Fragment {
    View mView;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    EditText emailtxt,passtxt,compasstxt,nombretxt,apellidostxt;
    TextView textopass, textoemail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.formulario_registro, container, false );
        textoemail=(TextView)mView.findViewById(R.id.textoemail);
        Button registrar=(Button)mView.findViewById(R.id.registrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toastpositivo =Toast.makeText(getContext(),"Registrado con éxito. ¡Bienvenido!", Toast.LENGTH_SHORT);
                Toast toastnegativo =Toast.makeText(getContext(),"No ha podido registrarse", Toast.LENGTH_SHORT);

                emailtxt=(EditText)mView.findViewById(R.id.email);
                passtxt=(EditText)mView.findViewById(R.id.passtxt);
                compasstxt=(EditText)mView.findViewById(R.id.compasstxt);
                nombretxt=(EditText)mView.findViewById(R.id.nombretxt);
                apellidostxt=(EditText)mView.findViewById(R.id.apellidostxt);

                String emailstring=String.valueOf(emailtxt.getText());
                String passstring=String.valueOf(passtxt.getText());
                String compassstring=String.valueOf(compasstxt.getText());
                String nombrestring=String.valueOf(nombretxt.getText());
                String apellidosstring=String.valueOf(apellidostxt.getText());
                
                if (validateEmail(emailstring)==true && validateCompass(compassstring,passstring)==true && otherValidates(emailstring,nombrestring,apellidosstring)==true && comprobarExistencia(emailstring)==true){
                    HttpPostUsuario tareaAsync=new HttpPostUsuario(emailstring,passstring,compassstring,nombrestring,apellidosstring);
                    tareaAsync.execute();
                    Intent form = new Intent(getContext(), login.class);
                    startActivity(form);
                }else{
                    toastnegativo.show();
                }
            }
        });


        final Button infoboton=(Button)mView.findViewById(R.id.infoboton);
        textopass=(TextView)mView.findViewById(R.id.textopass);
        textoemail.setVisibility(View.INVISIBLE);
        textopass.setVisibility(View.INVISIBLE);
        infoboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textopass.getVisibility() == View.INVISIBLE){

                    textopass.setVisibility(View.VISIBLE);
                }else{
                    textopass.setVisibility(View.INVISIBLE);
                }
            }
        });

        return mView;
    }

    private class HttpPostUsuario extends AsyncTask<String, Void, String> {

        String resultado;
        String emailstring;
        String passstring;
        String compassstring;
        String nombrestring;
        String apellidosstring;

        public HttpPostUsuario(String emailstrin, String passstring, String compassstring, String nombrestring, String apellidosstring) {
            this.emailstring = emailstrin;
            this.passstring = passstring;
            this.compassstring = compassstring;
            this.nombrestring = nombrestring;
            this.apellidosstring = apellidosstring;
        }

        @Override
        protected String doInBackground(String... params) {
            System.out.println(emailstring);
            operacionesApi.postUsuario(nombrestring,apellidosstring,emailstring,passstring,"1","1110");
            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), "Bienvenido a repara Sevilla", Toast.LENGTH_LONG).show();
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

        public void setResultado(String[] resultado) {
            this.resultado = resultado;
        }

        //public String[] getResultado() {
        //    return resultado;
        //}

        @Override
        protected String[] doInBackground(String... params) {
            String[] result = operacionesApi.getEmails();
            setResultado(result);

            return this.resultado;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }

    public boolean comprobarExistencia(String email){

        HttpGetEmails tareaAsync = new HttpGetEmails(email);
        tareaAsync.execute();

        String[] data = new String[0];
        try {
            data = tareaAsync.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(int i =0;i<data.length;i++){
            if (data[i].equals("[\""+email+"\"]")){

                    
                    return false;
                }
            }
        return true;
    }

    public boolean otherValidates(String emailstring, String nombrestring, String apellidosstring) {

        Toast toastnegativo =Toast.makeText(getContext(),"NO se admiten campos vacíos", Toast.LENGTH_SHORT);
        Toast toastnegativo2 =Toast.makeText(getContext(),"Nombre y/o Apellidos NO pueden ser numéricos", Toast.LENGTH_SHORT);


        if(isNumeric(nombrestring)==true || isNumeric(apellidosstring)==true){
            return false;
        }else{
            if(emailstring.equals("") || nombrestring.equals("") || apellidosstring.equals("")){
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

        if (validatePass(pass)==true && validatePass(compass)==true){
            if (pass.equals(compass)){
                toastpositivo.show();
                return true;
            }else {
                toastnegativo.show()
                return false;
            }
        }else {
       
            return false;
        }
    }
}
