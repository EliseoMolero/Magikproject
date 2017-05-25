package com.example.montxu.magik_repair;



import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 24/05/17.
 */

public class operacionesApi {

    public String[] getUsuario(String email){

        String url="http://192.168.1.133/get/usuario";
        String[] result = new String[6];
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);


            JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);
            Object nombre = jsonObjRecv.get("nombre");
            Object apellidoss = jsonObjRecv.get("apellidos");
            Object password = jsonObjRecv.get("passsword");
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

    public String[] getIncidencia(String email){

        String url="http://192.168.1.133/get/incidencias";
        String[] result = new String[7];
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);


            JSONObject jsonObjRecv = HttpClient.SendHttpPost(url, jsonObject);
            Object descripcion = jsonObjRecv.get("descripcion");
            Object direccion = jsonObjRecv.get("direccion");
            Object imagen = jsonObjRecv.get("imagen");
            Object latitud = jsonObjRecv.get("latitud");
            Object longitud = jsonObjRecv.get("longitud");
            Object id = jsonObjRecv.get("id");




            result[0] = String.valueOf(id);
            result[1] = String.valueOf(descripcion);
            result[2] = String.valueOf(direccion);
            result[3] = String.valueOf(imagen);
            result[4] = String.valueOf(latitud);
            result[5] = String.valueOf(longitud);
            result[6] = email;




        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void postIncidencia(String descripcion, String direccion, String imagen, String latitud, String longitud, String email){

        String url="http://192.168.43.132:5001/post/incidencias";
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("descripcion", descripcion);
            jsonObject.put("direccion", direccion);
            jsonObject.put("imagen", imagen);
            jsonObject.put("latitud", latitud);
            jsonObject.put("longitud", longitud);
            jsonObject.put("email", email);


            HttpClient.SendHttpPost(url, jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void postUsuario(String nombre, String apellidos, String email, String password, String admin, String imagenPerfil){

        String url="http://192.168.1.133/post/usuario";
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

        String url="http://192.168.1.133/put/usuario";
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

        String url="http://192.168.1.133/del/usuario";
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);


            HttpClient.SendHttpPost(url, jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
