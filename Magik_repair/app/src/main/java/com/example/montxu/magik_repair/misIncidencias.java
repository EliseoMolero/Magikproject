package com.example.montxu.magik_repair;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.R.*;


/**
 * Created by montxu on 24/05/17.
 */


public class misIncidencias extends Fragment{

    View mView;
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mis_incidencias, container, false );
        gridView = (GridView) mView.findViewById(R.id.gridview);


        Intent i = getActivity().getIntent();
        Usuario miUsuario = (Usuario) i.getSerializableExtra("usuario");
        String email=miUsuario.getEmail();
        HttpGetIncidencias tareaAsync = new HttpGetIncidencias(email);
        tareaAsync.execute();
        try {
            String[] incidencias = tareaAsync.get();
            for (int j = 0; j <incidencias.length; j++) {
                String[] datos = new String[3];
                Object a = incidencias[j];
                String dic_a= a.toString();
                dic_a = dic_a.replace("\\", "");
                dic_a = dic_a.replace("\"","");
                String[] b = dic_a.split(":");
                String descripcion = b[1].split(",")[0];
                String direccion = b[2].split(",")[0];
                String estado = b[4].split(",")[0];
                datos[0] = "Descripcion: "+descripcion;
                datos[1] = "Direccion: "+direccion;
                datos[2] = "Estado: "+estado;
                ArrayAdapter adaptador=new ArrayAdapter(this.getContext(), layout.simple_list_item_1,datos);
                gridView.setAdapter(adaptador);

            }

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
