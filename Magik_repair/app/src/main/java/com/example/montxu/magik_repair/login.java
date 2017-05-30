package com.example.montxu.magik_repair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class login extends AppCompatActivity {

    EditText Temail;
    EditText Tpassword;
    Button entrar;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Temail = (EditText) findViewById(R.id.textEmail);
        Tpassword = (EditText) findViewById(R.id.textPassword);
        entrar = (Button) findViewById(R.id.buttonLogin);
        registrar = (Button) findViewById(R.id.buttonRegistro);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Temail.getText().toString();
                String password = Tpassword.getText().toString();
                HttpGetEmails tareaAsync = new HttpGetEmails(email);
                tareaAsync.execute();
                String[] data;
                try {
                    data = tareaAsync.get();
                    password = password.replace(" ","");
                    System.out.println(data[3]);
                    System.out.println(password);
                    if (data[3].equals(password)) {
                        Intent form = new Intent(getApplicationContext(), Pantalla_Principal.class);
                        startActivity(form);
                    }else {
                        Toast.makeText(login.this, "Correo o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Toast.makeText(login.this, "Listo", Toast.LENGTH_SHORT).show();


            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent form = new Intent(getApplicationContext(), nuevo_user.class);
                startActivity(form);

            }
        });
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
}
