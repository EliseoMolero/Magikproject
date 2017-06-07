package com.example.montxu.magik_repair;

import java.io.Serializable;

/**
 * Created by root on 7/06/17.
 */

public class Incidencias implements Serializable {

    public String ids;
    public String descripcion;
    public String direccion;
    public String imagen;
    public String latitud;
    public String longitud;
    public String email;
    public String estado;

    public Incidencias(String ids, String descripcion, String direccion, String imagen, String latitud, String longitud, String email, String estado) {
        this.ids = ids;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.email = email;
        this.estado = estado;
    }

    public Incidencias(String descripcion, String latitud, String longitud) {
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
