package com.pe.af.android.antifraude.model;

import java.util.List;

public class IdentidadModel {
    private String nroDocumento;
    private String persona;
    private String transaccion;
    private String estado;
    private List<PreguntaModel> listaPreguntas;

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

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

    public List<PreguntaModel> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<PreguntaModel> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
}
