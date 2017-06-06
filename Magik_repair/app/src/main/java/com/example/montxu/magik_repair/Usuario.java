package com.example.montxu.magik_repair;

import java.io.Serializable;

/**
 * Created by root on 30/05/17.
 */

public class Usuario implements Serializable {

    public String ids;
    public String nombre;
    public String apellidos;
    public String password;
    public String admin;
    public String imagenPerfil;
    public String email;

    public Usuario(String email, String ids, String nombre, String apellidos, String password, String admin, String imagenPerfil) {
        this.ids = ids;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.admin = admin;
        this.imagenPerfil = imagenPerfil;
        this.email = email;
    }

    public Usuario() {
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}
