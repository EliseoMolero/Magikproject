package com.example.montxu.magik_repair;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.R.*;


/**
 * Created by montxu on 24/05/17.
 */


public class misIncidencias extends Fragment{

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mis_incidencias, container, false );
        Intent i = getActivity().getIntent();
        Usuario miUsuario = (Usuario) i.getSerializableExtra("usuario");
        String email=miUsuario.getEmail();
        HttpGetIncidencias tareaAsync = new HttpGetIncidencias(email);
        tareaAsync.execute();
        try {
            String[] incidencias = tareaAsync.get();
            System.out.print(incidencias[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return mView;
    }

    private class HttpGetIncidencias extends AsyncTask<String, Object, String[]> {

        String[] resultado;
        String input;

        public HttpGetIncidencias(String input) {
            this.input = input;
        }

        public String[] getResultado() {
            return resultado;
        }

        @Override
        protected String[] doInBackground(String... params) {

            String[] result = operacionesApi.getIncidencias(this.input);
            this.resultado = result;


            return result;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }
}
