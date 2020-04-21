package com.pe.af.android.data.dao;

import com.orm.SugarRecord;

public class AdmPreguntaDao extends SugarRecord {
    private Integer nroPregunta;
    private String pregunta;
    private boolean seleccionada;

    public Integer getNroPregunta() {
        return nroPregunta;
    }

    public void setNroPregunta(Integer nroPregunta) {
        this.nroPregunta = nroPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
}
