package com.pe.af.android.data.dao;

import com.orm.SugarRecord;

public class UsuarioDao extends SugarRecord {
    private String idUsuario;
    /*private String usuario;*/
    private String nombre;
    /*private String apellidos;
    private String correo;*/
    private String clave;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
