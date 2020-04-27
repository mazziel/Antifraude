package com.pe.af.android.antifraude.model;

import java.util.List;
import java.util.Map;

public class PreguntaModel {
    private Integer nroPregunta;
    private String pregunta;
    private Map<String, Object> opciones;
    private List<OpcionModel> alternativas;

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

    public List<OpcionModel> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<OpcionModel> alternativas) {
        this.alternativas = alternativas;
    }
}
