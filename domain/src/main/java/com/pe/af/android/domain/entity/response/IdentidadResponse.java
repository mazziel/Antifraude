package com.pe.af.android.domain.entity.response;

import com.pe.af.android.domain.entity.Pregunta;

import java.util.List;

public class IdentidadResponse {
    private String persona;
    private String transaccion;
    private String estado;
    private List<Pregunta> listaPreguntas;

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
}
