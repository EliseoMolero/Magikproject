package com.example.montxu.magik_repair;



import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 24/05/17.
 */

public class operacionesApi {

    public static String[] getEmails(){


        String url="http://192.168.0.104:5001/get/emails";



            JSONObject jsonObject = new JSONObject();

            JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);

            String[] result = new String[jsonObjRecv.length()];

            for (int i = 0; i < jsonObjRecv.length() ; i++) {
                try {
                    Object a = jsonObjRecv.get(String.valueOf(i));
                    result[i]=String.valueOf(a);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        return result;
    }
    

    public static String[] getUsuario(String email){

        String url="http://192.168.0.104:5001/get/usuario";
        String[] result = new String[7];
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);


            JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);
            Object nombre = jsonObjRecv.get("nombre");
            Object apellidoss = jsonObjRecv.get("apellidos");
            Object password = jsonObjRecv.get("password");
            Object admin = jsonObjRecv.get("admin");
            Object imagenPerfil = jsonObjRecv.get("imagenPerfil");
            Object id = jsonObjRecv.get("id");


            result[0] = String.valueOf(id);
            result[1] = String.valueOf(nombre);
            result[2] = String.valueOf(apellidoss);
            result[3] = String.valueOf(password);
            result[4] = String.valueOf(admin);
            result[5] = String.valueOf(imagenPerfil);
            result[6] = email;





        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String[] getIncidencias(String email){

        String url="http://192.168.0.105:5001/get/incidencias";


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);
        String[] result = new String[jsonObjRecv.length()];


        for (int i = 0; i < jsonObjRecv.length() ; i++) {
            try {

                Object a = jsonObjRecv.get(String.valueOf(i));
                result[i]=String.valueOf(a);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    public static void postIncidencia(String descripcion, String direccion, String imagen, String latitud, String longitud, String email, String estado){

        String url="http://192.168.0.105:5001/post/incidencias";
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("descripcion", descripcion);
            jsonObject.put("direccion", direccion);
            jsonObject.put("imagen", imagen);
            jsonObject.put("latitud", latitud);
            jsonObject.put("longitud", longitud);
            jsonObject.put("email", email);
            jsonObject.put("estado", estado);


            HttpClient.SendHttpPost(url, jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void postUsuario(String nombre, String apellidos, String email, String password, String admin, String imagenPerfil){

        String url="http://192.168.1.133:5001/post/usuario";
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", nombre);
            jsonObject.put("apellidos", apellidos);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("admin", admin);
            jsonObject.put("imagenPerfil", imagenPerfil);


            HttpClient.SendHttpPost(url, jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void putUsuario(String id, String nombre, String apellidos, String email, String password, String admin, String imagenPerfil){


        String url="http://192.168.0.104:5001/put/usuario";

        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", nombre);
            jsonObject.put("apellidos", apellidos);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("admin", admin);
            jsonObject.put("imagenPerfil", imagenPerfil);
            jsonObject.put("id", id);


            HttpClient.SendHttpPost(url, jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void delIncidencias(String id){

        String url="http://192.168.1.133:5001/del/usuario";
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);


            HttpClient.SendHttpPost(url, jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
        public static String[] getFullIncidencias(){
        String url="http://192.168.0.104:5001/get/fincidencias";


        JSONObject jsonObject = new JSONObject();

        JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);
        String[] result = new String[jsonObjRecv.length()];

            for (int i = 0; i < jsonObjRecv.length() ; i++) {
                try {

                    Object a = jsonObjRecv.get(String.valueOf(i));
                    result[i]=String.valueOf(a);

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        return result;
    }

    

    public static String getCorreo(String ids){
        String url="http://192.168.0.105:5001/get/emailId";
        JSONObject jsonObject = new JSONObject();
        System.out.println(ids);
        try {
            jsonObject.put("id", ids);
            JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);
            String[] result = new String[jsonObjRecv.length()];
            Object a = null;
            try {
                a = jsonObjRecv.get("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result[0]=String.valueOf(a);
            return result[0];
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String[] getUser(String ids){
        String url="http://192.168.0.105:5001/get/usuarioId";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", ids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);
        String[] result = new String[jsonObjRecv.length()];
        Object a = null;
        try {
            Object email = jsonObjRecv.get("email");
            Object imagen = jsonObjRecv.get("imagenPerfil");
            Object nombre = jsonObjRecv.get("nombre");
            Object apellidos = jsonObjRecv.get("apellidos");
            Object pass = jsonObjRecv.get("password");
            result[0]=String.valueOf(email);
            result[1]=String.valueOf(imagen);
            result[2]=String.valueOf(nombre);
            result[3]=String.valueOf(apellidos);
            result[4]=String.valueOf(pass);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
