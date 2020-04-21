package com.pe.af.android.domain.entity.response;

import com.pe.af.android.domain.entity.AdmPregunta;

import java.util.List;

public class AdmPreguntaResponse {

    private int seleccionMinima;
    private List<AdmPregunta> listaPreguntas;

    public int getSeleccionMinima() {
        return seleccionMinima;
    }

    public void setSeleccionMinima(int seleccionMinima) {
        this.seleccionMinima = seleccionMinima;
    }

    public List<AdmPregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<AdmPregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
}
