package com.pe.af.android.domain.entity;

import java.util.Map;

public class Pregunta {
    private Integer nroPregunta;
    private String pregunta;
    private Map<String, Object> opciones;

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

    public Map<String, Object> getOpciones() {
        return opciones;
    }

    public void setOpciones(Map<String, Object> opciones) {
        this.opciones = opciones;
    }
}
