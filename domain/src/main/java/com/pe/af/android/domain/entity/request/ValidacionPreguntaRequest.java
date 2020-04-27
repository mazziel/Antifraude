package com.pe.af.android.domain.entity.request;

public class ValidacionPreguntaRequest {
    private int[] preguntasSeleccionadas;

    public int[] getPreguntasSeleccionadas() {
        return preguntasSeleccionadas;
    }

    public void setPreguntasSeleccionadas(int[] preguntasSeleccionadas) {
        this.preguntasSeleccionadas = preguntasSeleccionadas;
    }
}
